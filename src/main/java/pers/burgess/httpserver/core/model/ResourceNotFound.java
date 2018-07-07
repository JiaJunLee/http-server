package pers.burgess.httpserver.core.model;

/**
 * Created by 51998 on 2018/7/8.
 */
public class ResourceNotFound extends Exception {

    public ResourceNotFound() {}

    public ResourceNotFound(String exception) {
        super(exception);
    }

}
