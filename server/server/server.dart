import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as shelf_io;
import 'dart:io' as io;
import 'package:shelf_route/shelf_route.dart';
import 'package:shelf_exception_response/exception_response.dart';
import 'package:shelf_bind/shelf_bind.dart';
import 'routes/Recents.dart';

main() {

    Router rootRouter = router(handlerAdapter: handlerAdapter());
    rootRouter
        ..get('/', () => new Response.notFound('Hello ApkMirror!'))
        ..get('/recents', () => new Recents().get(7))
        ..get('/apks', () => new Response.notFound('NOT FOUND'));

    var handler = const Pipeline()
    .addMiddleware(logRequests())
    .addMiddleware(exceptionResponse())
    .addHandler(rootRouter.handler);

    printRoutes(rootRouter);

    shelf_io.serve(handler, io.InternetAddress.ANY_IP_V4, 4040).then((server) {
        print('Serving at http://${server.address.host}:${server.port}');
    });
}