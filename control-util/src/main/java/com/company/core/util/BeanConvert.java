package com.company.core.util;

import jodd.bean.BeanCopy;

public class BeanConvert {

    public static Object convertTo(Object origin, Object dest) {
        BeanCopy.beans(origin, dest).copy();
        return dest;
    }

    public static Object convertTo(Object origin, Object dest, String... name) {
        BeanCopy.beans(origin, dest).exclude(name).copy();
        return dest;
    }
    
    public static void copy(Object src, Object des) {
        BeanCopy.beans(src, des).copy();
    }
}
