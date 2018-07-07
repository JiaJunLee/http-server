package pers.burgess.httpserver.core.res;

import java.net.URL;

public class ForwardResource extends StaticResource {

    private String key;

    public ForwardResource(String key, URL url) {
        super(url);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return this.key + " - " + this.url;
    }

}
