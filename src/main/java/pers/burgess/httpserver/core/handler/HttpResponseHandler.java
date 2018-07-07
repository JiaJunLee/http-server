package pers.burgess.httpserver.core.handler;

import pers.burgess.httpserver.core.HttpResponse;
import pers.burgess.httpserver.core.HttpResponseCode;
import pers.burgess.httpserver.core.ResourceManager;
import pers.burgess.httpserver.core.model.ResourceNotFound;
import pers.burgess.httpserver.core.res.ForwardResource;
import pers.burgess.httpserver.core.res.Resource;
import pers.burgess.httpserver.core.res.StaticResource;
import pers.burgess.httpserver.core.res.StringResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by 51998 on 2018/7/8.
 */
public class HttpResponseHandler {

    private Resource resource = null;

    public HttpResponseHandler (Resource resource) {
        this.resource = resource;
    }

    public HttpResponse getHttpResponse () {
        HttpResponse httpResponse = new HttpResponse();

        if (this.resource instanceof ForwardResource) {
            httpResponse.setResponseCode(HttpResponseCode.MOVED);
        }

        try {
            httpResponse.setResourceStream(ResourceManager.getStream(this.resource));
        } catch (FileNotFoundException e) {
//            httpResponse.setResponseCode(HttpResponseCode.NOT_FOUND);
        } catch (ResourceNotFound resourceNotFound) {
//            httpResponse.setResponseCode(HttpResponseCode.NOT_FOUND);
        }

        try {
            if (this.resource instanceof StaticResource && !(this.resource instanceof ForwardResource)) {
                    httpResponse.getHeader().put("Content-Type", Files.probeContentType(Paths.get(((StaticResource)this.resource).getUrl().toURI())));
            } else if (this.resource instanceof StringResource) {
                httpResponse.getHeader().put("Content-Type", "text/plain");
            }
            httpResponse.getHeader().put("Content-Length", String.valueOf(httpResponse.getResourceStream().available()));
        } catch (Exception e) {
            httpResponse.setResponseCode(HttpResponseCode.UNAVAILABLE);
        }

        return httpResponse;
    }

}
