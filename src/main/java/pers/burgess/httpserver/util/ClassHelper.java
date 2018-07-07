package pers.burgess.httpserver.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by 51998 on 2018/7/7.
 */
public class ClassHelper {

    public static List<Class> getClasses(Class baseClass) throws IOException, ClassNotFoundException {
        String basePackage = baseClass.getPackage().getName();

        List<Class> classes = new ArrayList<Class>();

        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(basePackage.replaceAll("\\.", "/"));
        URL url = null;
        File file = null;
        while (dirs.hasMoreElements()) {

            url = dirs.nextElement();

            if (url.getProtocol().toUpperCase().equals("FILE")) {
                file = new File(url.getFile());
                scan(file, classes, basePackage);
            }

        }

        return classes;
    }

    private static void scan (File file, List<Class> classes, String basePackage) throws ClassNotFoundException {
        if (file == null || classes == null || !file.exists())
            return;
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                scan(f, classes, f.isDirectory() ? (basePackage + "." + f.getName()) : basePackage);
            }
        } else {
            if (file.getName().toUpperCase().endsWith(".CLASS")) {
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(basePackage + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
    }

}
