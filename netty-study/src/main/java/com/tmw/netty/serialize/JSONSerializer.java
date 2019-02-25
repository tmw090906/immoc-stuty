package com.tmw.netty.serialize;

import com.alibaba.fastjson.JSON;
import com.tmw.netty.common.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> classType, byte[] bytes) {
        return JSON.parseObject(bytes, classType);
    }

}
