package pers.burgess.httpserver.controller;

import pers.burgess.httpserver.core.HttpRequest;
import pers.burgess.httpserver.core.HttpRequestType;
import pers.burgess.httpserver.core.HttpResponse;
import pers.burgess.httpserver.core.method.Controller;
import pers.burgess.httpserver.core.method.Mapping;
import pers.burgess.httpserver.core.res.Resource;
import pers.burgess.httpserver.core.res.StringResource;

@Controller
public class BookController {

    @Mapping(value = "/books", method = HttpRequestType.GET)
    public Resource getBooks (HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

    @Mapping(value = "/book", method = HttpRequestType.GET)
    public Resource getBook (HttpRequest httpRequest, HttpResponse httpResponse) {
        return new StringResource("hello world");
    }

    @Mapping(value = "/create_book", method = HttpRequestType.POST)
    public Resource createBook (HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

    @Mapping(value = "/update_book", method = HttpRequestType.PUT)
    public Resource updateBook (HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

    @Mapping(value = "/delete_book", method = HttpRequestType.DEL)
    public Resource deleteBook (HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

}
