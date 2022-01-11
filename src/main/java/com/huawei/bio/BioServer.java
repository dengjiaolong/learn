package com.huawei.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    public static void main(String[] args) throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            //监听 等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程 与客户端保持连接
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    communication(socket);
                }
            });
        }

    }

    public static void communication(Socket socket) {

        try {
            byte[] buffer = new byte[1024];
            //得到输入流
            InputStream in = socket.getInputStream();
            //读取数据
            while (true) {
                int num = in.read(buffer);
                if (num != -1) {
                    System.out.println("读取的客户端数据是:" + new String(buffer, 0, num));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
