import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as shelf_io;
import 'dart:io' as io;
import 'package:shelf_route/shelf_route.dart';
import 'package:shelf_exception_response/exception_response.dart';
import 'package:shelf_bind/shelf_bind.dart';
import 'routes/Recents.dart';

io.InternetAddress ADDRESS = io.InternetAddress.ANY_IP_V4;
//io.InternetAddress ADDRESS = io.InternetAddress.LOOPBACK_IP_V4;
int PORT = 4040;

main() {
    Router rootRouter = router(handlerAdapter: handlerAdapter());
    rootRouter
        ..get('/', () => new Response.notFound('Hello ApkMirror!'))
        ..get('/recents', () => new Recents().get(20))
        ..get('/loaderio-92596b535f26ef8c84c13f0bb81fb937',
        () => new Response.ok('loaderio-92596b535f26ef8c84c13f0bb81fb937'))
        ..get('/apks', () => new Response.notFound('NOT FOUND'));

    var handler = const Pipeline()
    .addMiddleware(logRequests())
    .addMiddleware(exceptionResponse())
    .addHandler(rootRouter.handler);

    print('starting server at ${ADDRESS.address}:$PORT');
    shelf_io.serve(handler, ADDRESS, PORT).then((server) {
        print('Server is now running at http://${server.address.host}:${server.port}');

        print('\nRoutes:');
        printRoutes(rootRouter);
        print('\nLog:');
    }, onError: (io.HttpServer server, stack) {
        print('\nERROR: Could not start server.');
        print(stack);
    });
}