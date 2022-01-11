package com.huawei.nio;

import java.nio.IntBuffer;

public class NioBuffer {
    public static void main(String[] args) {

        //创建一个buffer 可以存放5个int类型数据
        IntBuffer buffer = IntBuffer.allocate(5);
        for (int i = 0; i < buffer.capacity(); i++) {
            //存放数据
            buffer.put(i+1);
        }

        //将buffer转换 读写切换
        buffer.flip();
        //读取buffer数据
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}
