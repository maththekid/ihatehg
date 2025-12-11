package me.viniciusroger.ihatehg.util.java;

public class JavaUtil {
    public static Number getNumberFromType(Number number, Class<?> type) {
        // "switch case com java.lang.Class só é suportada no jdk 21+"
        // vai se foder :thumbsup:
        if (type == Byte.class) {
            return number.byteValue();
        } else if (type == Short.class) {
            return number.shortValue();
        } else if (type == Integer.class) {
            return number.intValue();
        } else if (type == Long.class) {
            return number.longValue();
        } else if (type == Float.class) {
            return number.floatValue();
        } else {
            return number.doubleValue();
        }
    }
}
