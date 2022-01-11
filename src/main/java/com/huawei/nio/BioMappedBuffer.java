package com.huawei.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BioMappedBuffer {

    public static void main(String[] args) throws Exception{
        //参数1 文件名 2模式 rw 读写
        RandomAccessFile accessFile = new RandomAccessFile("src/main/resources/file/1.txt","rw");

        FileChannel channel = accessFile.getChannel();
        //参数1  模式  2 可以修改起始位置索引 3 修改的大小
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        //参数1 修改位置距离起始位置的距离  2修改成的值
        //修改的起始索引(3)+参数(2)=要修改的位置索引(5)
        map.put(2,(byte)'N');//01234N6789
        map.put(4, (byte) 'N');//预测结果 ：01234N6N89 ===> 实际输出结果：01234N6N89
        accessFile.close();
        System.out.println("修改成功");
    }
}
