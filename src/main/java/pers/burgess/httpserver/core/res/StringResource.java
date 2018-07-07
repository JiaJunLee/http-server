package pers.burgess.httpserver.core.res;

public class StringResource implements Resource {

    private String resource;

    public StringResource () {}

    public StringResource(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

}
