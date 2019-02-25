package com.tmw.netty.serialize;

public interface Serializer {

    /**
     * 默认使用JSON为序列化算法
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();


    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);


    /**
     * 二进制转化成java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
