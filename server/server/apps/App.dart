import '../util/Serializable.dart';
import 'dart:convert';

class App extends Object with Serializable {
    String name;
    String filename;
    String iconUrl;
    String downloadUrl;
    String listingUrl;
    String packageName;
    String versionName;
    String uploaded;
    num fileSize = 0;
    int minSdk = 0;
    String md5;
    String publisher;
    String sha1;
    String uploader;
    num downloads = 0;
    num version = 0;

    App(this.name, this.version);
}

main() { /*
     * date ::= yeardate time_opt timezone_opt
     * yeardate ::= year colon_opt month colon_opt day
     * year ::= sign_opt digit{4,5}
     * colon_opt :: <empty> | ':'
     * sign ::= '+' | '-'
     * sign_opt ::=  <empty> | sign
     * month ::= digit{2}
     * day ::= digit{2}
     * time_opt ::= <empty> | (' ' | 'T') hour minutes_opt
     * minutes_opt ::= <empty> | ':' digit{2} seconds_opt
     * seconds_opt ::= <empty> | ':' digit{2} millis_opt
     * millis_opt ::= <empty> | '.' digit{1,6}
     * timezone_opt ::= <empty> | space_opt timezone
     * space_opt :: ' ' | <empty>
     * timezone ::= 'z' | 'Z' | sign digit{2} timezonemins_opt
     * timezonemins_opt ::= <empty> | colon_opt digit{2}
     */
    var app = new App('Google Play services 6.1.88 (1557022-738)', 1)
        ..publisher = 'Google Inc.'
        ..filename = 'com.google.android.gms-6.1.88_(1557022-738)-6188738-minAPI21.apk'
        ..minSdk = 21
        ..uploaded = new DateTime.now().toIso8601String()
        ..md5 = '34d98ac7145aa9585a81d4a1ddeebe9c'
        ..sha1 = '1ddac1b9dab102422a88b930e85608098dfcaca4'
        ..downloads = 2859
        .. uploader = 'dave';

    print('now: ${new DateTime.now().toUtc()}');
    String json = JSON.encode(app);
    print(json);
}