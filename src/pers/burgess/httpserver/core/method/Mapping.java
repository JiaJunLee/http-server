package pers.burgess.httpserver.core.method;

import pers.burgess.httpserver.core.HttpRequestType;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapping {

    String value() default "/";
    HttpRequestType method() default HttpRequestType.GET;

}
