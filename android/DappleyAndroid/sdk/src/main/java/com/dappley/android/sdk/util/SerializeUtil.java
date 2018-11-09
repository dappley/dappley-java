package com.dappley.android.sdk.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Object serialize util.
 */
public class SerializeUtil {

    /**
     * Serialize object
     * @param obj
     * @return byte[] byte array
     */
    public static byte[] encode(Object obj) {
        Kryo kryo = new Kryo();
        Output output = new Output(1024);
        kryo.writeObject(output, obj);
        byte[] bytes = output.toBytes();
        output.close();
        return bytes;
    }

    /**
     * Deserialize object
     * @param bytes byte array
     * @param clazz object Class name
     * @param <T> the type of object
     * @return T object
     */
    public static <T> T decode(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        Input input = new Input(bytes);
        T t = kryo.readObject(input, clazz);
        input.close();
        return t;
    }

    /**
     * Serialize HashSet
     * @param set object
     * @param clazz object Class name
     * @param <T> the type of object
     * @return byte[] byte array
     */
    public static <T> byte[] encodeSet(Set<T> set, Class<T> clazz) {
        Kryo kryo = new Kryo();
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, set);
        output.flush();
        output.close();
        byte[] bytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Deserialize HashSet
     * @param bytes byte array
     * @param clazz object Class name
     * @param <T> the type of elements in set
     * @return HashSet<T>
     */
    public static <T> HashSet<T> decodeSet(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);
        Input input = new Input(bytes);
        HashSet<T> t = kryo.readObject(input, HashSet.class, serializer);
        input.close();
        return t;
    }

    /**
     * Serialize ArrayList
     * @param list object list
     * @param clazz object Class name
     * @param <T> the type of object
     * @return byte[] byte array
     */
    public static <T extends Serializable> byte[] encodeList(List<T> list, Class<T> clazz) {
        Kryo kryo = new Kryo();
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, list);
        output.flush();
        output.close();
        byte[] bytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Deserialize ArrayList
     * @param bytes byte array
     * @param clazz object Class name
     * @param <T> the type of elements in set
     * @return List<T>
     */
    public static <T extends Serializable> List<T> decodeList(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);
        Input input = new Input(bytes);
        List<T> t = kryo.readObject(input, ArrayList.class, serializer);
        input.close();
        return t;
    }
}
