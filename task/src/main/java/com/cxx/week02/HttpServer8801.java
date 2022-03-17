package com.cxx.week02;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName HttpServer8801
 * @Author
 * @Date 2022/3/6 22:26
 */
public class HttpServer8801 {
    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket( 8801);
        while (true) {
            try {
                Socket socket = serversocket.accept();
                service (socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        private static void service(Socket socket) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Content-Type:text/htmL;charset=utf-8");
                        String body = "hello,niol";
                printWriter.println("Content-Length:" + body.getBytes().length);
                printWriter.println();
                printWriter.write(body);
                printWriter.close();
                socket.close();
            } catch (IOException e) { // I InterruptedException e) {
                e.printStackTrace();
            }
        }
}
