# ApkMirror server

A simple server written in [Dart](http://dartlang.org) to serve app update information from http://www.apkmirror.com/ to the Android App.

## Install & startup

#### install dart (OSX)

```bash
brew tap dart-lang/dart
brew install dart
```

#### start the server

```bash
pub get
dart bin/server.dart
```

#### open in IntelliJ


Open Project -> Choose the `apkmirror-android/server` folder

Right click `pubspec.yaml` -> Pub: Get Dependencies

run `bin/server.dart`
