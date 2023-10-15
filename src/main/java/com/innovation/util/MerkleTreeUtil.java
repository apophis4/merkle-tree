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
        for (byte[] arr : arrays) {
            length += arr.length;
        }
        byte[] result = new byte[length];
        int position = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, position, arr.length);
            position += arr.length;
        }
        return result;
    }

    public static <T> boolean isWrapperClass(T object){
        return primitiveList.contains(object.getClass());
    }

}
