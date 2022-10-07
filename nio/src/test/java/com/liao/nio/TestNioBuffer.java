package com.liao.nio;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Buffer 就像一个数组，可以保存多个相同类型的数据。根据数据类型不同(boolean 除外) ，有以下Buffer 常用子类
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 */
public class TestNioBuffer {

    ByteBuffer byteBuffer;
    private final static int CAPACITY = 1024;

    @Before
    public void before() {
        byteBuffer = ByteBuffer.allocate(CAPACITY);
    }


    /**
     * 缓冲区的父类Buffer中有几个核心属性，如下
     * private int mark = -1;
     * private int position = 0;
     * private int limit;
     * private int capacity;
     * capacity：缓冲区的容量。通过构造函数赋予，一旦设置，无法更改
     * limit：缓冲区的界限。位于limit 后的数据不可读写。缓冲区的限制不能为负，并且不能大于其容量
     * position：下一个读写位置的索引（类似PC）。缓冲区的位置不能为负，并且不能大于limit
     * mark：记录当前position的值。position被改变后，可以通过调用reset() 方法恢复到mark的位置。
     * 以上四个属性必须满足以下要求
     * mark <= position <= limit <= capacity
     */
    @Test
    public void test01() {
        byte[] bs = "hello".getBytes(Charset.defaultCharset());
        // put方法将数据存放进行bytebuffer中
        byteBuffer.put(bs);
        int limit = byteBuffer.limit();
        int capacity = byteBuffer.capacity();
        int position = byteBuffer.position();
        System.out.printf("bs len = %d,limit = %d, capacity = %d, position = %d \n",
                bs.length, limit, capacity, position);
        Assert.assertEquals(bs.length, position);
        Assert.assertEquals(limit, capacity);
        Assert.assertEquals(capacity, CAPACITY);
    }

    @Test
    public void test02() {
        byte[] bs = "hello".getBytes(Charset.defaultCharset());
        // put方法将数据存放进行bytebuffer中
        byteBuffer.put(bs);
        // 切换为读模式
        byteBuffer.flip();
        // flip 之后 limit变成最后bye所在的位置
        int limit = byteBuffer.limit();
        // position为没有读取的开始的位置
        int position = byteBuffer.position();
        // capacity 仍然是开辟的最大空间大小
        int capacity = byteBuffer.capacity();
        Assert.assertEquals(0, position);
        Assert.assertEquals(bs.length, limit);
        Assert.assertEquals(capacity, CAPACITY);
        // 读取所有的数据
        int len = limit - position;
        byte[] data = new byte[len];
        // 标记 reset之后 position指针重新指向这里
        byteBuffer.mark();
        for (int i = 0; i < len; i++) {
            data[i] = byteBuffer.get();
        }
        Assert.assertEquals("hello", new String(data));
        // reset 重置到mark的位置
        byteBuffer.reset();

        limit = byteBuffer.limit();
        position = byteBuffer.position();
        capacity = byteBuffer.capacity();

        Assert.assertEquals(0, position);
        Assert.assertEquals(bs.length, limit);
        Assert.assertEquals(capacity, CAPACITY);
        // 一次性读取
        data = new byte[limit - position];
        byteBuffer.get(data);
        Assert.assertEquals("hello", new String(data));


        limit = byteBuffer.limit();
        position = byteBuffer.position();
        capacity = byteBuffer.capacity();

        // 此时已经读取完毕了 position=limit
        Assert.assertEquals(limit, position);
        Assert.assertEquals(bs.length, limit);
        Assert.assertEquals(capacity, CAPACITY);

        // 在flip模式下 可以直接使用rewind函数 重置为开始读取的位置
        byteBuffer.rewind();

        limit = byteBuffer.limit();
        position = byteBuffer.position();
        capacity = byteBuffer.capacity();

        Assert.assertEquals(0, position);
        Assert.assertEquals(bs.length, limit);
        Assert.assertEquals(capacity, CAPACITY);

        // 输入开辟的byte数组 包括没有放置数据的位置 长度为1024 不会更改position指针位置
        byte[] array = byteBuffer.array();
        Assert.assertEquals("hello", new String(array, position, limit));


        Assert.assertEquals(0, position);
        Assert.assertEquals(bs.length, limit);
        Assert.assertEquals(capacity, CAPACITY);

        // clean()方法会将缓冲区中的各个属性恢复为最初的状态，position = 0, capacity = limit
        // 此时缓冲区的数据依然存在，处于“被遗忘”状态，下次进行写操作时会覆盖这些数据
        byteBuffer.clear();

        limit = byteBuffer.limit();
        position = byteBuffer.position();
        capacity = byteBuffer.capacity();

        Assert.assertEquals(0, position);
        Assert.assertEquals(capacity, limit);
        Assert.assertEquals(capacity, CAPACITY);
    }

    @Test
    public void test03() {
        // 非直接缓冲区
        // 通过allocate()方法获取的缓冲区都是非直接缓冲区。这些缓冲区是建立在JVM堆内存之中的。
        ByteBuffer bf1 = ByteBuffer.allocate(CAPACITY);
        Assert.assertFalse(bf1.isDirect());
        // 直接缓冲区
        ByteBuffer bf2 = ByteBuffer.allocateDirect(CAPACITY);
        Assert.assertTrue(bf2.isDirect());
    }

}
