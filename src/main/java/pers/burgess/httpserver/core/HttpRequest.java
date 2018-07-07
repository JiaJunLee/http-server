package pers.burgess.httpserver.core;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {

    protected HttpRequestType requestType;
    private String remoteAddress = null;
    private URL url = null;

    public HttpRequest () {
        super(null, new HashMap<String, String>(), new HashMap<String, String>());
    }

    public HttpRequest(HttpRequestType requestType, String version, Map<String, String> header, Map<String, String> parameters) {
        super(version, header, parameters);
        this.requestType = requestType;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}
