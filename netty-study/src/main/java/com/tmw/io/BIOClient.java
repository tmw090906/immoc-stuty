package com.tmw.io;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BIOClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("127.0.0.1", 8000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            socket.getOutputStream().write((simpleDateFormat.format(new Date()) + ":Hello world!").getBytes("UTF-8"));
            Thread.sleep(10000);
        }


    }

}
