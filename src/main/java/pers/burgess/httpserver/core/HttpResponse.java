package pers.burgess.httpserver.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse extends HttpMessage {

    private String responseCode = HttpResponseCode.SUCCESS;
    private InputStream resourceStream = null;

    public HttpResponse () {
        super("HTTP/1.1", new HashMap<String, String>(), new HashMap<String, String>());
    }

    public HttpResponse(String responseCode, String version, Map<String, String> header, Map<String, String> parameters) {
        super(version, header, parameters);
        this.responseCode = responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResourceStream(InputStream resourceStream) {
        this.resourceStream = resourceStream;
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        if (this.resourceStream != null) {
            int len = -1;
            byte[] buff = new byte[2048];

            InputStream headerStream = new ByteArrayInputStream(this.toString().getBytes());

            while ((len = headerStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }

            while ((len = this.resourceStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }

            outputStream.flush();
            headerStream.close();
            this.resourceStream.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(this.version + " " + this.responseCode + "\r\n");
        for (String key: this.header.keySet()) {
            headerBuilder.append(key + ": " + this.header.get(key) + "\r\n");
        }
        headerBuilder.append("\r\n");
        return headerBuilder.toString();
    }

}
