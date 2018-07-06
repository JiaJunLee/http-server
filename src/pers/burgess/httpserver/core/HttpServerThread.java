package pers.burgess.httpserver.core;

import pers.burgess.httpserver.core.handler.HttpRequestHandler;

import java.io.IOException;
import java.net.Socket;

public class HttpServerThread implements Runnable {

    private Socket socket = null;

    public HttpServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (this.socket != null) {
            try {
                HttpRequest httpRequest = new HttpRequestHandler(this.socket.getInputStream()).getHttpRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
