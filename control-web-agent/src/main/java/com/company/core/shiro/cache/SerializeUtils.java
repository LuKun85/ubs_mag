package com.company.core.shiro.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.apache.shiro.codec.Base64;


public class SerializeUtils extends SerializationUtils {

    public static String serializeToString(Serializable obj) {
        try {
            byte[] value = serialize(obj);
            return Base64.encodeToString(value);
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    public static <T> T deserializeFromString(String base64) {
        try {
            byte[] objectData = Base64.decode(base64);
            return (T) deserialize(objectData);
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }

    public static <T> Collection<T> deserializeFromStringController(Collection<String> base64s) {
        try {
            List<T> list = new LinkedList<T>();
            for (String base64 : base64s) {
                byte[] objectData = Base64.decode(base64);
                T t = (T) deserialize(objectData);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }
}