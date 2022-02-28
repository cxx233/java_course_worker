package com.cxx.jvm;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xujiaxi <br>
 * @version 1.0 <br>
 * @description: MyClassLoder <br>
 * @date 2022/2/28 11:37 <br>
 */
public class XClassLoader extends ClassLoader {

    public static void main(String[] args) throws IOException {

        try {
            // 为什么invode 不能这个o？
            Object o = new XClassLoader().findClass("Hello").newInstance();
            Class helloClass = new XClassLoader().findClass("Hello");
            Method method = helloClass.getMethod("hello");
            method.setAccessible(true);
            method.invoke(helloClass.newInstance());
            Method[] methods = helloClass.getMethods();
            for (Method m : methods) {
                System.out.printf("name: %s, params: %d \r\n",m.getName(), m.getParameterCount());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // resources 下的文件
//        URL url = XClassLoader.class.getClassLoader().getResource("Hello.xlass");


//        if (!file.exists()) {
//            System.out.println("file not exists");
//            return;
//        }
//        System.out.println("file exists");
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        String xlass = null;
//        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            InputStream inputStream = new FileInputStream(file)
////            InputStream inputStream = XClassLoader.class.getResourceAsStream("Hello.xlass")
//        ) {
//            while ((len = inputStream.read(buffer)) != -1) {
//                bos.write(buffer, 0, len);
//            }
//            xlass = new String(bos.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (xlass != null && xlass.length() != 0) {
//            System.out.println("xlass:" + xlass);
//        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        URL url = XClassLoader.class.getResource("/Hello.xlass");
        try {
            System.out.println(URLDecoder.decode(url.getPath(), "utf-8"));
            File file = new File(URLDecoder.decode(url.getFile(), "utf-8"));
            Path path = Paths.get(file.getAbsolutePath());
            byte[] datas = Files.readAllBytes(path);
            byte[] convertDatas = new byte[datas.length];
            for (int i = 0; i < datas.length; i++) {
                byte r = (byte) (255 - datas[i]);
                convertDatas[i] = r;
            }
            return defineClass(name, convertDatas, 0, convertDatas.length);
        } catch (Exception e) {
            //
        }
        return null;
    }
}
