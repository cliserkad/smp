package com.xarql.smp;

import test.java.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xarql.smp.SimpleEncoder.*;

public class GenericParser {

    /**
     * Simple test against Car
     * @param args ignored
     * @throws IllegalAccessException when ToSMP.encode() fails
     */
    public static void main(String[] args) throws IllegalAccessException {
        final String smp = encode(new Car());
        if(Verifier.verifyOrPrint(smp)) {
            Map<StringPath, Object> data = parse(smp);
            System.out.println(data.get(new StringPath("year")));
            System.out.println(data.get(new StringPath("model")));
            System.out.println(data.get(new StringPath("driveModes")));
        }
        final String smp2 = encode(new SimpleEncoder());
        System.out.println(prettyPrint(smp2));
        Map<StringPath, Object> data2 = parse(smp2);
        System.out.println(data2.get(new StringPath("CHAR_QUOTE")));

        System.out.println();
        System.out.println();

        System.out.println(prettyPrint(encode(new Car())));
    }

    public static Map<StringPath, Object> parse(String smp) {
        return parse(new StringPath(), smp);
    }

    /**
     * Creates a Map which contains the data from an smp encoded string.
     * This makes smp values queryable in an easy to understand manner.
     *
     * @param smp A string representing any data encoded in smp
     * @return a Map with keys and values from smp
     */
    public static Map<StringPath, Object> parse(StringPath currentPath, String smp) {
        final Map<StringPath, Object> out = new HashMap<>();

        boolean inStringLit = false;
        boolean inCharLit = false;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < smp.length(); i++) {
            if(smp.charAt(i) == BACKSLASH) {
                i++;
                builder.append(smp.charAt(i));
            } else {
                if(smp.charAt(i) == CHAR_QUOTE && !inStringLit)
                    inCharLit = !inCharLit;
                else if(smp.charAt(i) == QUOTE && !inCharLit)
                    inStringLit = !inStringLit;
                if(!inCharLit && !inStringLit) {
                    if(smp.charAt(i) == ASSIGN) {
                        currentPath = currentPath.append(builder.toString());
                        builder.setLength(0);
                    } else if(smp.charAt(i) == SEPARATOR) {
                        out.putAll(parse(currentPath.delete(), currentPath.last() + ":" + builder.toString() + ";"));
                        builder.setLength(0);
                        int depth = Integer.parseInt(currentPath.last());
                        currentPath = currentPath.delete().append(depth + 1 + "");
                    } else if(smp.charAt(i) == PAIR_END) {
                        out.put(currentPath.copy(), parsePrimitive(builder.toString()));
                        builder.setLength(0);
                        currentPath = currentPath.delete();
                    } else if(smp.charAt(i) == LIST_START) {
                        currentPath = currentPath.append("" + 0);
                    } else if(smp.charAt(i) == LIST_END) {
                        currentPath = currentPath.delete();
                    } else if(!Character.isWhitespace(smp.charAt(i)) && smp.charAt(i) != OBJ_START)
                        builder.append(smp.charAt(i));
                }
                else
                    builder.append(smp.charAt(i));
            }
        }

        return out;
    }

    /**
     * Attempts to match value text to its appropriate Java data type.
     *
     * @param primitive A string representing a primitive data structure
     * @return An object with appropriate type and value
     */
    public static Object parsePrimitive(String primitive) {
        if(primitive.equals(""))
            return null;
        if(primitive.equals("" + OBJ_END))
            return new PlaceHolder();
        if(primitive.charAt(0) == CHAR_QUOTE) {
            if(primitive.charAt(1) == BACKSLASH)
                return primitive.charAt(2);
            else
                return primitive.charAt(1);
        }
        try {
            return Integer.parseInt(primitive);
        } catch(NumberFormatException e) {
            // do nothing
        }
        try {
            return Float.parseFloat(primitive);
        } catch(NumberFormatException e) {
            // do nothing
        }
        try {
            return Long.parseLong(primitive);
        } catch(NumberFormatException e) {
            // do nothing
        }
        try {
            return Double.parseDouble(primitive);
        } catch(NumberFormatException e) {
            // do nothing
        }
        return primitive.substring(1, primitive.length() - 1);
    }

}
