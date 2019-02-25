package com.tmw.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

    private static final Integer PORT = 8000;

    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(PORT);


        while (true) {
            try {
                // 1) 阻塞方式获取新的连接
                Socket socket = serverSocket.accept();

                // 2) 每个新的连接都建一个线程负责读取数据
                new Thread(() -> {
                    try {
                        int len;
                        byte[] data = new byte[1024];
                        InputStream inputStream =  socket.getInputStream();

                        while ((len = inputStream.read(data)) != -1) {
                            System.out.println(new String(data, 0, len));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}
