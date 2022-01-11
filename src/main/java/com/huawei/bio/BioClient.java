package com.huawei.bio;

import java.io.OutputStream;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) {

        try {
            String host = "127.0.0.1";
            int port = 6666;
            // 与服务端建立连接
            Socket socket = new Socket(host, port);
            // 建立连接后获得输出流
            OutputStream out = socket.getOutputStream();
            String message = "你好,服务器";
            out.write(message.getBytes("UTF-8"));
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
