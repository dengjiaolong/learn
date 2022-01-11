package com.huawei.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BioCopy {

    public static void main(String[] args) {

       try {
           File file = new File("src/main/resources/file/2.txt");
           FileOutputStream out = new FileOutputStream(file);
           FileChannel outChannel = out.getChannel();

           FileInputStream in = new FileInputStream(new File("src/main/resources/file/1.txt"));
           FileChannel inChannel = in.getChannel();

           ByteBuffer buffer = ByteBuffer.allocate(512);

           int size = 0;
           while (size>=0){
               //重置缓存区 指针复位
                buffer.clear();
               //管道数据写入缓存区
               size=inChannel.read(buffer);
               System.out.println(size);
               //切换状态
               buffer.flip();
               //缓存区数据写入管道
               outChannel.write(buffer);
           }
           in.close();
           out.close();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
