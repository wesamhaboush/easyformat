package com.codebreeze.text.furmat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This simple class is based on slf4j pattern and code. It was modified to work with java 8, and simplified
 * for simple formatting
 */
public class TextFormat {
    private static final char DELIM_START = '{';
    private static final char DELIM_END = '}';
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';
    private static final char SEP_CHAR = '/';

    public static String textFormat(final String messagePattern, final Object arg){
        return format(messagePattern, arg);
    }

    public static String textFormat(final String messagePattern, final Object arg1, final Object arg2){
        return format(messagePattern, arg1, arg2);
    }

    public static String textFormat(final String messagePattern, final Object arg1, final Object arg2, final Object arg3){
        return format(messagePattern, arg1, arg2, arg3);
    }

    public static String textFormat(final String messagePattern,
                               final Object arg1,
                               final Object arg2,
                               final Object arg3,
                               final Object arg4){
        return format(messagePattern, arg1, arg2, arg3, arg4);
    }

    public static String textFormat(final String messagePattern,
                               final Object arg1,
                               final Object arg2,
                               final Object arg3,
                               final Object arg4,
                               final Object arg5){
        return format(messagePattern, arg1, arg2, arg3, arg4, arg5);
    }

    public static String format(final String messagePattern, final Object arg) {
        return arrayFormat(messagePattern, new Object[]{arg});
    }

    public static String apply(final String messagePattern, final Object arg){
        return format(messagePattern, arg);
    }

    public static String apply(final String messagePattern, final Object arg1, final Object arg2){
        return format(messagePattern, arg1, arg2);
    }

    public static String apply(final String messagePattern, final Object arg1, final Object arg2, final Object arg3){
        return format(messagePattern, arg1, arg2, arg3);
    }

    public static String apply(final String messagePattern,
                               final Object arg1,
                               final Object arg2,
                               final Object arg3,
                               final Object arg4){
        return format(messagePattern, arg1, arg2, arg3, arg4);
    }

    public static String apply(final String messagePattern,
                               final Object arg1,
                               final Object arg2,
                               final Object arg3,
                               final Object arg4,
                               final Object arg5){
        return format(messagePattern, arg1, arg2, arg3, arg4, arg5);
    }

    public static String format(final String messagePattern, final Object arg1, final Object arg2) {
        return arrayFormat(messagePattern, new Object[]{arg1, arg2});
    }

    public static String format(final String messagePattern,
                                final Object arg1,
                                final Object arg2,
                                final Object arg3) {
        return arrayFormat(messagePattern, new Object[]{arg1, arg2, arg3});
    }

    public static String format(final String messagePattern,
                                final Object arg1,
                                final Object arg2,
                                final Object arg3,
                                final Object arg4) {
        return arrayFormat(messagePattern, new Object[]{arg1, arg2, arg3, arg4});
    }

    public static String format(final String messagePattern,
                                final Object arg1,
                                final Object arg2,
                                final Object arg3,
                                final Object arg4,
                                final Object arg5) {
        return arrayFormat(messagePattern, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }


    private static String arrayFormat(final String messageTemplate,
                                     final Object[] argArray) {

        if (emptyMessageTemplate(messageTemplate)) {
            return arrayFormat(repeatDelimiterStr(argArray.length), argArray);
        }

        int i = 0;
        final StringBuilder sb = new StringBuilder(messageTemplate.length() + 50);

        int L;
        for (L = 0; L < argArray.length; L++) {

            int j = messageTemplate.indexOf(DELIM_STR, i);

            if (noMoreVariables(j)) {
                // no more variables
                if (noPlaceholders(i)) { // this is a simple string
                    return messageTemplate;
                } else {
                    break;
                }
            } else {
                if (isEscapedDelimeter(messageTemplate, j)) {
                    if (!isDoubleEscaped(messageTemplate, j)) {
                        L--; // DELIM_START was escaped, thus should not be incremented
                        sb.append(messageTemplate.substring(i, j - 1));
                        sb.append(DELIM_START);
                        i = j + 1;
                    } else {
                        // The escape character preceding the delimiter start is
                        // itself escaped: "abc x:\\{}"
                        // we have to consume one backward slash
                        sb.append(messageTemplate.substring(i, j - 1));
                        appendBasedOnTypeInformation(sb, argArray[L], new HashMap<>());
                        i = j + 2;
                    }
                } else {
                    // no escape, continue as usual
                    sb.append(messageTemplate.substring(i, j));
                    appendBasedOnTypeInformation(sb, argArray[L], new HashMap<>());
                    i = j + 2;
                }
            }
        }
        // append the characters following the last {}.
        sb.append(messageTemplate.substring(i, messageTemplate.length()));
        if (L < argArray.length) {
            sb.append(format(" -> WARNING: unused parameters: message pattern [{}], parameters{}", messageTemplate, argArray));
        }
        return sb.toString();
    }

    private static boolean emptyMessageTemplate(final String messageTemplate) {
        return messageTemplate == null || messageTemplate.length() == 0 || messageTemplate.trim().length() == 0;
    }

    private static boolean noPlaceholders(final int i) {
        return i == 0;
    }

    private static boolean noMoreVariables(final int j) {
        return j == -1;
    }

    private static boolean isEscapedDelimeter(final String messagePattern, final int delimeterStartIndex) {
        if (delimeterStartIndex == 0) {
            return false;
        }
        final char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
        return potentialEscape == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(final String messagePattern, final int delimeterStartIndex) {
        return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
    }

    private static void appendBasedOnTypeInformation(final StringBuilder sb, final Object o, final Map<Object[], Object> seenMap) {
        if (o == null) {
            sb.append("null");
        }
        else if (!o.getClass().isArray()) {
            sb.append(Objects.toString(o));
        } else {
            // primitive arrays cannot be case to Object[]
            if (o instanceof boolean[]) {
                append(sb, (boolean[]) o);
            } else if (o instanceof byte[]) {
                append(sb, (byte[]) o);
            } else if (o instanceof char[]) {
                append(sb, (char[]) o);
            } else if (o instanceof short[]) {
                append(sb, (short[]) o);
            } else if (o instanceof int[]) {
                append(sb, (int[]) o);
            } else if (o instanceof long[]) {
                append(sb, (long[]) o);
            } else if (o instanceof float[]) {
                append(sb, (float[]) o);
            } else if (o instanceof double[]) {
                append(sb, (double[]) o);
            } else {
                append(sb, (Object[]) o, seenMap);
            }
        }
    }

    private static void append(final StringBuilder sb, final Object[] a, final Map<Object[], Object> seenMap) {
        sb.append('[');
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null);
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                appendBasedOnTypeInformation(sb, a[i], seenMap);
                if (i != len - 1)
                    sb.append(", ");
            }
            // allow repeats in siblings
            seenMap.remove(a);
        } else {
            sb.append("...");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final boolean[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final byte[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final char[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final short[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final int[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final long[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, final float[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static void append(final StringBuilder sb, double[] a) {
        sb.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sb.append(a[i]);
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(']');
    }

    private static String repeatDelimiterStr(final int repeat) {
        if (repeat == 1) {
            return DELIM_STR;
        }

        final int outputLength = DELIM_STR.length() * repeat + 1;
        final StringBuilder sb = new StringBuilder(outputLength);
        sb.append(SEP_CHAR);
        for (int i = 0; i < repeat; i++) {
            sb.append(DELIM_START).append(DELIM_END).append(SEP_CHAR);
        }
        return sb.toString();
    }
}
