package com.huawei.nio;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioChannel {

    public static void main(String[] args) {
        readBuffer();
        writeBuffer();

    }
    //将缓存区的数据读出来
    private static void readBuffer() {
        try {
            String str = "hello,world";
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\1.txt");
            //得到fileChannel
            FileChannel channel = fileOutputStream.getChannel();
            //初始byte缓存区大小
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //将数据放入缓冲区
            byteBuffer.put(str.getBytes());
            //切换buffer状态 初始化position位置和limit大小
            byteBuffer.flip();
            //将缓存区数据写入channel
            channel.write(byteBuffer);
            //释放资源
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //将数据写入缓存区
    private static void writeBuffer() {
        try {
            File file =new File("d:\\1.txt");
            FileInputStream in= new FileInputStream(file);
            FileChannel channel = in.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            //将通道的数据写入缓存区
            channel.read(buffer);
            System.out.println(new String(buffer.array()));
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
