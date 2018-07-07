package pers.burgess.httpserver.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pers.burgess.httpserver.core.handler.RouteBus;
import pers.burgess.httpserver.core.model.Configuration;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class HttpServer {

    private static final Logger logger = LogManager.getLogger(HttpServer.class);

    private Class<?> cls = null;
    private Configuration configuration = null;
    private RouteBus routeBus = null;
    private ServerSocket serverSocket = null;
    private boolean isRunning = false;

    public HttpServer (Class<?> cls) throws IOException {
        this.cls = cls;

        initializeConfiguration();
        initializeRouteBus();

        this.serverSocket = new ServerSocket(this.configuration.getPort());
    }

    private void initializeConfiguration() throws MalformedURLException {
        logger.info("loading server configuration");

        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/server.properties"));
            this.configuration = new Configuration(properties);
        } catch (IOException e) {
            logger.info("server.properties not exists, use default server configuration");
            this.configuration = new Configuration();
        }

        logger.info(this.configuration);
    }

    private void initializeRouteBus() throws IOException {
        logger.info("initialize route bus");

        if (this.configuration != null) {
            try {
                this.routeBus = new RouteBus(cls, this.configuration);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("route bus initialize failed");
            }
        }
    }

    public void start () {
        logger.info("http server running at: http://localhost:" + this.configuration.getPort());
        this.isRunning = true;
        while (isRunning) {
            Socket socket = null;
            try {
                socket = this.serverSocket.accept();
                new Thread(new HttpServerThread(socket, routeBus)).start();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }


}
