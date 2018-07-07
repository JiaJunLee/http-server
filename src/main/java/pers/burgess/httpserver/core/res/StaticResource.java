package pers.burgess.httpserver.core.res;

import java.net.URL;

public class StaticResource implements Resource {

    URL url = null;

    public StaticResource (URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

}
