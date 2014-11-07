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

class Recents {

    int number;

    List<App> apps = new List();

    Recents();

    Future<Recents> get(final int number) {

        Completer<Recents> requestCompleter = new Completer();

        new HttpClient().get('www.apkmirror.com', 80, 'feed').then((HttpClientRequest request) {
            return request.close();
        }).then((response) {

            Completer<List<String>> completer = new Completer();
            List<String> appLinks = new List();
            response.transform(UTF8.decoder).listen((contents) {
                //print(contents);

                XmlElement element = XML.parse(contents);
                var items = element.queryAll('item').queryAll('link').forEach((XmlNode node) {
                    String toParseLink = node.toString();
                    //print(toParseLink);
                    RegExp exp = new RegExp(r'<link>(.*)<\/link>');
                    Iterable<Match> matches = exp.allMatches(toParseLink);
                    //print(matches.length);
                    //print(matches.last.group(1));

                    if (!matches.isEmpty) {
                        var link = matches.last.group(1);
                        //print(link);
                        appLinks.add(link);
                    }

                    /*print(node.toString());
                RegExp exp = new RegExp(r'<link>(.*)<');
                Iterable<Match> matches = exp.allMatches(node.toString());
                print(matches.last.input);
                //appLinks.add(matches.last.input);*/

                    //XmlElement xmlnode = XML.parse(node.toString());
                    //print(xmlnode);

                    //print(appLinks.length);
                });
                print(appLinks);
                //var items = element();

                //Iterable<Future> futures = new Iterable.generate(appLinks.length, (i) => new HttpClient().get(appLinks.));
                //Future.wait(requestFutures);

            }, onDone: () {
                completer.complete(appLinks);
            });

            return completer.future;
        }).then((List<String> appLinks) {
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
                print("pages: ${responses.length}");
                this.number = responses.length;
                for (String response in responses) {
                    apps.add(parseInfoHtml(response));
                }
                requestCompleter.complete(this);

            });
        });

        return requestCompleter.future;
    }

    Map toJson() => {
        'number' : number,
        'apps' : apps
    };
}

App parseInfoHtml(String first) {
    Document document = parse(first);
    Element element = document.getElementsByClassName('post-area').first;
    String title = element.getElementsByTagName('h1').first.text;
    var element1 = element.getElementsByTagName('img').first;
    String iconLink = element1.attributes.remove('src');
    String infos = element.text;
    String version = new RegExp(r'Version: (.*)').allMatches(infos).last.group(1);
    String fileName = new RegExp(r'File name: (.*)').allMatches(infos).last.group(1);
    String uploaded = new RegExp(r'Uploaded: (.*)').allMatches(infos).last.group(1);
    String fileSize = new RegExp(r'File size: (.*)').allMatches(infos).last.group(1);
    String minSdkVersion = new RegExp(r'Minimum Android version: (.*)').allMatches(infos).last.group(1);
    String md5 = new RegExp(r'MD5: (.*)').allMatches(infos).last.group(1);
    String sha1 = new RegExp(r'SHA1: (.*)').allMatches(infos).last.group(1);
    String downloads = new RegExp(r'Downloads: (.*)').allMatches(infos).last.group(1);
    String uploader = new RegExp(r'Uploaded by: (.*)').allMatches(infos).last.group(1);

    App app = new App(title, 1)
        ..publisher = 'Google Inc.'
        ..filename = fileName
        ..minSdk = 1
        ..uploaded = new DateTime.now().toIso8601String()
        ..md5 = md5
        ..sha1 = sha1
        ..downloads = 190123
        ..iconUrl = iconLink
        .. uploader = uploader;
    print('app: ${app.toJson()}');
    return app;
}

main() {



}