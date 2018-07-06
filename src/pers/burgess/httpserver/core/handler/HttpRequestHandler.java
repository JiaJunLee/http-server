package pers.burgess.httpserver.core.handler;

import pers.burgess.httpserver.core.HttpRequest;
import pers.burgess.httpserver.core.HttpRequestType;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HttpRequestHandler {

    InputStream inputStream = null;

    public HttpRequestHandler (InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest getHttpRequest () throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        List<String> headerList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            headerList.add(line);
        }

        HttpRequest httpRequest = new HttpRequest();

        if (headerList.size() > 1) {
            String[] tmp = null;
            for (int i=1; i<headerList.size(); i++) {
                tmp = headerList.get(i).split(": ");
                // tmp should be like: Host: 127.0.0.1:8080
                if (tmp.length == 2) {
                    httpRequest.getHeader().put(tmp[0], tmp[1]);
                }
            }
        }

        if (headerList.size() > 0) {
            // initialize base information from request header first line
            String[] base = headerList.get(0).split(" ");
            // http request first line should be like: GET /index.html HTTP/1.1
            if (base.length == 3) {
                switch (base[0].toUpperCase().trim()) {
                    case "GET":
                        httpRequest.setRequestType(HttpRequestType.GET);
                        break;
                    case "POST":
                        httpRequest.setRequestType(HttpRequestType.POST);
                        break;
                    case "PUT":
                        httpRequest.setRequestType(HttpRequestType.PUT);
                        break;
                    case "DEL":
                        httpRequest.setRequestType(HttpRequestType.DEL);
                        break;
                    default:
                        httpRequest.setRequestType(HttpRequestType.GET);
                }
                String[] host = httpRequest.getHeader().get("Host").split(":");
                httpRequest.setUrl(new URL("http", host[0], (host.length > 1) ? Integer.valueOf(host[1]) : 80, base[1]));
                httpRequest.setVersion(base[2]);
            }
        }

        if (httpRequest.getUrl() != null) {
            String url = httpRequest.getUrl().toString();
            String[] tmp = null;
            for (String param: url.substring(url.indexOf("?") + 1).split("&")) {
                tmp = param.split("=");
                if (tmp.length == 2) {
                    httpRequest.getParameters().put(tmp[0], tmp[1]);
                }
            }
        }

        if (httpRequest.getHeader().containsKey("content-length")) {
            char[] postMsg =  new char[Integer.valueOf(httpRequest.getHeader().get("content-length"))];
            bufferedReader.read(postMsg);
            System.out.println(new String(postMsg));
        }
        return httpRequest;
    }

}
