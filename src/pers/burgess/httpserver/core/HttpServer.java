package pers.burgess.httpserver.core;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9978);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new HttpServerThread(socket)).start();
        }
    }

}
