package com.cxx.jvm;


import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class JvmClassLoaderPrintPath {
    public static void main(String[] args) {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器 BootstrapClassLoder");
        for (URL url : urls) {
            System.out.println("===>" + url.toExternalForm());
        }

        printClassLoader("拓展类加载器 ExtClassLoader", JvmClassLoaderPrintPath.class.getClassLoader().getParent());
        printClassLoader("应用类加载器 AppClassLoader", JvmClassLoaderPrintPath.class.getClassLoader());


    }

    private static void printClassLoader(String name, ClassLoader cl) {
        if (cl != null) {
            System.out.println(name + " ClassLoader  -> " + cl.toString());
            printURLForClassLoader(cl);
        } else {
            System.out.println(name + " ClassLoader -> null" );
        }
    }

    private static void printURLForClassLoader(ClassLoader cl) {
        Object ucp = insignField(cl, "ucp");
        Object path = insignField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> " + p.toString());
        }
    }

    private static Object insignField(Object obj, String fName) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return  f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
