package com.innovation.util;

import java.math.BigDecimal;
import java.util.*;

public class MerkleTreeUtil {

    private static List<Class> primitiveList = new ArrayList<>();
    static {
        primitiveList.add(java.lang.Integer.class);
        primitiveList.add(java.lang.String.class);
        primitiveList.add(java.lang.Short.class);
        primitiveList.add(java.math.BigDecimal.class);
        primitiveList.add(java.lang.Long.class);
        primitiveList.add(java.lang.Double.class);
        primitiveList.add(java.lang.Character.class);
        primitiveList.add(java.lang.Boolean.class);
    }

    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

    public static <T> boolean isWrapperClass(T object){
        System.out.println(primitiveList.contains(object.getClass()));
        return primitiveList.contains(object.getClass());
    }

}
