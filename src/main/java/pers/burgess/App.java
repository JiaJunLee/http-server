package pers.burgess;

import pers.burgess.httpserver.core.HttpServer;
import pers.burgess.httpserver.core.handler.RouteBus;
import pers.burgess.httpserver.util.ClassHelper;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by 51998 on 2018/7/7.
 */
public class App {

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer(App.class);
        server.start();
    }

}
