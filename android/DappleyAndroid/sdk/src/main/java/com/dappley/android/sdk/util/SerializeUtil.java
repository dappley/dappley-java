package com.dappley.android.sdk.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializeUtil {

    public static byte[] encode(Object obj) {
        Kryo kryo = new Kryo();
        Output output = new Output(1024);
        kryo.writeObject(output, obj);
        byte[] bytes = output.toBytes();
        output.close();
        return bytes;
    }

    public static <T> T decode(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        Input input = new Input(bytes);
        T t = kryo.readObject(input, clazz);
        input.close();
        return t;
    }
}
