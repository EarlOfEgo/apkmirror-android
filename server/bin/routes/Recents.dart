import 'package:shelf_route/shelf_route.dart';
import 'package:shelf/shelf.dart';
import 'dart:async';
import '../apps/App.dart';
import 'dart:math' as math;
import 'dart:convert';
import 'dart:io';
import 'package:xml/xml.dart';
import 'package:html5lib/parser.dart' show parse;
import 'package:html5lib/dom.dart';

const String RECENT_URL = 'http://www.apkmirror.com/apps_post-sitemap.xml';

Future<String> parseResponseToString(HttpClientResponse response) {
    final Completer<String> completer = new Completer();
    StringBuffer buffer = new StringBuffer();
    response.transform(UTF8.decoder).listen((String data) {
        buffer.write(data);
    }, onDone: () {
        completer.complete(buffer.toString());
    });
    return completer.future;
}

String removePiLine(final String xmlString) {
    num position = xmlString.indexOf('\n');
    final String xmlData = xmlString.substring(position + 1, xmlString.length);
    return xmlData;
}

class Recents {

    static void addAppIfNewer(App app, List<App> apps) {
        bool isOlder = false;
        for (App addedApp in apps) {
            if (addedApp.isSameApp(app)) {
                //print('found same app $addedApp : $app');
                if (app.isNewerThan(addedApp)) {
                    apps.remove(addedApp);
                    apps.add(app);
                } else {
                    isOlder = true;
                }
                break;
            }
        }
        if (!isOlder) {
            apps.add(app);
        }
    }

    static DateTime recentsChangedDate = new DateTime.fromMillisecondsSinceEpoch(0);

    static List<App> apps = new List();

    Recents();

    Future<Recents> get(final int number) {

        Completer<Recents> requestCompleter = new Completer();

        new HttpClient().getUrl(Uri.parse('http://www.apkmirror.com/sitemap_index.xml'))
        .then((HttpClientRequest request) {
            return request.close();
        })
        .then((HttpClientResponse response) {
            return parseResponseToString(response);
        }).then((String xmlData) {
            xmlData = removePiLine(xmlData);
            XmlElement sitemapsOverview = XML.parse(xmlData);
            XmlNode urlNode = sitemapsOverview.queryAll('loc').where((XmlNode element) {
                return element.toString().contains(RECENT_URL);
            }).first;
            XmlNode lastMod = urlNode.parent.children.query('lastmod').first;
            RegExp exp = new RegExp(r'>(.*)<\/');
            Iterable<Match> matches = exp.allMatches(lastMod.toString());
            String timestamp = matches.last.group(1);
            DateTime date = DateTime.parse(timestamp);
            if (!date.isAfter(recentsChangedDate)) {

                //cache hit
                requestCompleter.complete(this);
            } else {
                recentsChangedDate = date;
                Future<List<String>> recentRequest = new HttpClient().getUrl(Uri.parse(RECENT_URL))
                .then((
                    HttpClientRequest request) {
                    return request.close();
                })
                .then((response) => parseResponseToString(response))
                .then((String xmlData) {
                    List<String> appLinks = new List();
                    xmlData = removePiLine(xmlData);
                    XmlElement element = XML.parse(xmlData);
                    var items = element.queryAll('url').queryAll('loc')
                    .reversed.take(number * 2).forEach((XmlNode node) {
                        String toParseLink = node.toString();
                        RegExp exp = new RegExp(r'<loc>(.*)<\/loc>');
                        Iterable<Match> matches = exp.allMatches(toParseLink);
                        if (!matches.isEmpty) {
                            var link = matches.last.group(1);
                            appLinks.add(link);
                        }
                    });

                    return new Future.value(appLinks);
                });

                return recentRequest;
            }
        }).then((List<String> appLinks) {
            if (appLinks == null) {
                return null;
            }
            List<Future> appRequests = new List();

            for (String link in appLinks) {
                Future linkRequest = new HttpClient().getUrl(Uri.parse(link)).then((
                    HttpClientRequest request) {
                    return request.close();
                }).then((HttpClientResponse response) {

                    Completer<String> completer = new Completer();
                    StringBuffer content = new StringBuffer();
                    response.transform(UTF8.decoder).listen((String contents) {
                        content.write(contents);
                    }, onDone: () {
                        completer.complete(content.toString());
                    });

                    return completer.future;
                });
                appRequests.add(linkRequest);
            }

            return Future.wait(appRequests).then((List<String> responses) {
                List<App> foundApp = new List();
                for (String response in responses) {
                    var app = parseInfoHtml(response);
                    addAppIfNewer(app, foundApp);
                }
                apps.clear();
                apps.insertAll(0, foundApp.take(number));
                print('Loaded ${apps.length} Recent Apps');
                requestCompleter.complete(this);
            });
        }).catchError(() {
            recentsChangedDate = new DateTime.fromMillisecondsSinceEpoch(0);
        });

        return requestCompleter.future;

    }

    Map toJson() => {
        'number' : apps.length,
        'apps' : apps
    };

}

App parseInfoHtml(String first) {
    Document document = parse(first);
    Element element = document.getElementsByClassName('post-area').first;
    String title = element.getElementsByTagName('h1').first.text;
    var img = element.getElementsByTagName('img').first;
    String iconLink = img.attributes.remove('src');
    String originalIconUri = Uri.parse(iconLink).queryParameters.values.first;
    String downloadLink = element.getElementsByClassName('downloadButton').first.attributes.remove('href');
    String playStoreLink = element.getElementsByClassName('downloadButtons').first.getElementsByTagName('a').first.attributes.remove('href');
    String packageName = new RegExp(r'details\?id=(.*)').firstMatch(playStoreLink).group(1);
    String infos = element.text;
    String version = new RegExp(r'Version: (.*)').allMatches(infos).last.group(1);
    int appVersion = int.parse(new RegExp(r'\((\d+)\)').allMatches(version).last.group(1));
    String versionName = new RegExp(r'(.*) \(').firstMatch(version).group(1);
    String fileName = new RegExp(r'File name: (.*)').allMatches(infos).last.group(1);
    String fileSize = new RegExp(r'File size: (.*)').allMatches(infos).last.group(1);
    num fileSizeInByte = num.parse(new RegExp(r'\((.*) bytes')
    .firstMatch(fileSize.replaceAll(r',', '')).group(1));
    String uploaded = new RegExp(r'Uploaded: (.*)').allMatches(infos).last.group(1);
    String minSdkVersionText = new RegExp(r'Minimum Android version: (.*)').allMatches(infos).last.group(1);
    int minSdkVersion = int.parse(new RegExp(r'API (.*)\)').firstMatch(minSdkVersionText).group(1));
    String md5 = new RegExp(r'MD5: (.*)').allMatches(infos).last.group(1);
    String sha1 = new RegExp(r'SHA1: (.*)').allMatches(infos).last.group(1);
    int downloads = int.parse(new RegExp(r'Downloads: (.*)').allMatches(infos)
    .last.group(1).replaceAll(r',', ''));
    String uploader = new RegExp(r'Uploaded by: (.*)').allMatches(infos).last.group(1);

    App app = new App(title, 1)
        ..publisher = 'Google Inc.'
        ..filename = fileName
        ..minSdk = minSdkVersion
        ..uploaded = new DateTime.now().toIso8601String()
        ..md5 = md5
        ..version = appVersion
        ..versionName = versionName
        ..sha1 = sha1
        ..downloads = downloads
        ..iconUrl = originalIconUri
        ..fileSize = fileSizeInByte
        ..packageName = packageName
        ..listingUrl = playStoreLink
        ..downloadUrl = downloadLink
        ..uploader = uploader;
    //print('app: ${app.toJson()}');
    return app;
}

main() {
    new Recents().get(1);

}