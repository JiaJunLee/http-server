package pers.burgess.httpserver.core;

import pers.burgess.httpserver.core.res.HttpMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {

    private URL url = null;

    public HttpRequest () {
        super(HttpRequestType.GET, null, new HashMap<String, String>(), new HashMap<String, String>());
    }

    public HttpRequest(HttpRequestType requestType, String version, Map<String, String> header, Map<String, String> parameters) {
        super(requestType, version, header, parameters);
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}
