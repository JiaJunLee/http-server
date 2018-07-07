package pers.burgess.httpserver.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pers.burgess.httpserver.core.handler.HttpRequestHandler;
import pers.burgess.httpserver.core.handler.HttpResponseHandler;
import pers.burgess.httpserver.core.handler.RouteBus;
import pers.burgess.httpserver.core.model.ResourceNotFound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class HttpServerThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(HttpServerThread.class);

    private Socket socket = null;
    private RouteBus routeBus = null;

    public HttpServerThread(Socket socket, RouteBus routeBus) {
        this.socket = socket;
        this.routeBus = routeBus;
    }

    @Override
    public void run() {
        if (this.socket != null) {
            InputStream is = null;
            OutputStream os = null;
            HttpResponse httpResponse = null;
            try {
                is = this.socket.getInputStream();
                os = this.socket.getOutputStream();

                HttpRequest httpRequest = new HttpRequestHandler(is).getHttpRequest();
                httpResponse = new HttpResponseHandler(this.routeBus.route(httpRequest)).getHttpResponse();
                httpResponse.writeTo(os);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ResourceNotFound pageNotFound) {
                System.err.println("not found");
            } finally {
                clear(is, os);
            }
        }
    }

    private void clear (InputStream is, OutputStream os) {
        try {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
            if (this.socket != null)
                this.socket.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

}
