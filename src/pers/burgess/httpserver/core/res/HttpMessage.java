package pers.burgess.httpserver.core.res;

import pers.burgess.httpserver.core.HttpRequestType;

import java.util.Map;

public abstract class HttpMessage {

    protected HttpRequestType requestType;
    protected String version;
    protected Map<String, String> header;
    protected Map<String, String> parameters;

    public HttpMessage (HttpRequestType requestType, String version, Map<String, String> header, Map<String, String> parameters) {
        this.requestType = requestType;
        this.version = version;
        this.header = header;
        this.parameters = parameters;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
