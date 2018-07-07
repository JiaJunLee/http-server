package pers.burgess.httpserver.core.handler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pers.burgess.httpserver.core.HttpRequest;
import pers.burgess.httpserver.core.HttpResponseCode;
import pers.burgess.httpserver.core.method.Controller;
import pers.burgess.httpserver.core.method.Mapping;
import pers.burgess.httpserver.core.model.Configuration;
import pers.burgess.httpserver.core.model.ResourceNotFound;
import pers.burgess.httpserver.core.res.ForwardResource;
import pers.burgess.httpserver.core.res.Resource;
import pers.burgess.httpserver.core.res.StaticResource;
import pers.burgess.httpserver.util.ClassHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51998 on 2018/7/7.
 */
public class RouteBus {

    private static final Logger logger = LogManager.getLogger(RouteBus.class);

    private Configuration configuration = null;
    private Map<String, Method> routeMap = null;
    private Map<String, Object> controllerMap = null;

    public RouteBus(Class<?> cls, Configuration configuration) throws IOException, IllegalAccessException, InstantiationException {
        this.configuration = configuration;
        this.routeMap = new HashMap<String, Method>();
        this.controllerMap = new HashMap<String, Object>();

        logger.info("route bus scanning package classes");
        List<Class> classes = null;
        try {
            classes = ClassHelper.getClasses(cls);
        } catch (ClassNotFoundException e) {
            logger.error("can not load classes");
        }

        for (Class c: classes) {
            if (c.isAnnotationPresent(Controller.class)) {
                logger.info("find controller: " + c.getName());
                this.controllerMap.put(c.getName(), c.newInstance());

                for (Method m: c.getMethods()) {
                    if (m.isAnnotationPresent(Mapping.class)) {
                        Mapping mapping = (Mapping)m.getAnnotation(Mapping.class);

                        if (routeMap.containsKey(mapping.value())) {
                            StringBuilder exceptionMsg = new StringBuilder("\nroute conflict at:\n");
                            exceptionMsg.append(routeMap.get(mapping.value()).getDeclaringClass().getName() + " -> " + routeMap.get(mapping.value()).getName() + " -> [" + mapping.value() + "]\n");
                            exceptionMsg.append(m.getDeclaringClass().getName() + " -> " + m.getName() + " -> [" + mapping.value() + "]");
                            throw new RuntimeException(exceptionMsg.toString());
                        }

                        logger.info("add route: [" + mapping.value() + "], call method: " + m.getName());
                        routeMap.put(mapping.value(), m);
                    }
                }
            }
        }

    }

    public Resource route (HttpRequest httpRequest) throws MalformedURLException, InvocationTargetException, IllegalAccessException, ResourceNotFound {

        String requestPath = httpRequest.getUrl().getPath().trim();

        // to welcome page
        if (requestPath.equals("/")) {
            File welcomeFile = null;
            for (String welcome: this.configuration.getWelcomes()) {
                welcomeFile = new File(this.configuration.getRootPath(), welcome);
                if (welcomeFile.exists()) {
                    return new StaticResource(welcomeFile.toURI().toURL());
                }
            }
            return this.configuration.getSystemResourceMap().get(HttpResponseCode.NOT_FOUND);
        }

        // static file
        File staticFile = new File(this.configuration.getRootPath(), requestPath);
        if (staticFile.exists() && staticFile.isFile()) {
            return new StaticResource(staticFile.toURI().toURL());
        }

        // forward path
        for (ForwardResource forwardResource: this.configuration.getForwardResources()) {
            if (forwardResource.getKey().equals(requestPath)) {
                return forwardResource;
            }
        }

        // controller
        for (String key: this.routeMap.keySet()) {
            if (requestPath.equals(key)) {
                Method method = this.routeMap.get(key);
                return (Resource) method.invoke(this.controllerMap.get(this.routeMap.get(key).getDeclaringClass().getName()), httpRequest, null);
            }
        }

        return this.configuration.getSystemResourceMap().get(HttpResponseCode.NOT_FOUND);
    }

}
