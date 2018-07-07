package pers.burgess.httpserver.core.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pers.burgess.httpserver.core.HttpResponseCode;
import pers.burgess.httpserver.core.res.ForwardResource;
import pers.burgess.httpserver.core.res.SystemResource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by 51998 on 2018/7/8.
 */
public class Configuration {

    private static final Logger logger = LogManager.getLogger(Configuration.class);

    private int port;
    private File rootPath;
    private String[] welcomes;
    private List<ForwardResource> forwardResources;
    private File systemResPath;
    private Map<String, SystemResource> systemResourceMap;

    public Configuration() throws MalformedURLException {
        this.port = 8080;
        this.rootPath = new File(new File(getClass().getResource("/").getFile()), "web");
        this.welcomes = new String[]{ "index.html" };
        this.forwardResources = new ArrayList<ForwardResource>();
        this.systemResPath = new File(new File(getClass().getResource("").getFile()).getParentFile(), "page");
        this.systemResourceMap = new HashMap<String, SystemResource>();
        this.systemResourceMap.put(HttpResponseCode.NOT_FOUND, new SystemResource(new File(this.systemResPath, "404.html").toURI().toURL()));
        this.systemResourceMap.put(HttpResponseCode.UNAVAILABLE, new SystemResource(new File(this.systemResPath, "500.html").toURI().toURL()));
    }

    public Configuration(Properties properties) throws MalformedURLException {
        this();
        if (properties.containsKey("port")) {
            this.port = Integer.valueOf(properties.getProperty("port").trim());
        }
        if (properties.containsKey("rootpath")) {
            this.rootPath = new File(properties.getProperty("rootpath").trim());
        }
        if (properties.containsKey("welcome")) {
            this.welcomes = properties.getProperty("welcome").split(";");
            for (int i=0; i<welcomes.length; i++)
                this.welcomes[i] = this.welcomes[i].trim();
        }
        for (Object k: properties.keySet()) {
            if (k.toString().startsWith("forward")) {
                try {
                    this.forwardResources.add(new ForwardResource(k.toString().substring(k.toString().indexOf(".") + 1), new URL(properties.getProperty(k.toString()))));
                } catch (MalformedURLException e) {
                    logger.error("can not parse forward resource: " + properties.getProperty(k.toString()));
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public File getRootPath() {
        return rootPath;
    }

    public String[] getWelcomes() {
        return welcomes;
    }

    public List<ForwardResource> getForwardResources() {
        return forwardResources;
    }

    public Map<String, SystemResource> getSystemResourceMap() {
        return systemResourceMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("port: " + this.port + ", ");
        sb.append("rootPath: " + this.rootPath.getAbsolutePath() + ", ");
        sb.append("welcomes: " + String.join(", ", this.welcomes) + ", ");
        sb.append("forward resources: " + this.forwardResources.toString());
        return sb.toString();
    }

}
