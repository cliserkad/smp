package com.xarql.smp;

import test.java.Car;

import java.util.HashMap;
import java.util.Map;

import static com.xarql.smp.ToSMP.*;

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
        }
    }

    /**
     * Creates a Map which contains the data from an smp encoded string.
     * This makes smp values queryable in an easy to understand manner.
     *
     * @param smp A string representing any data encoded in smp
     * @return a Map with keys and values from smp
     */
    public static Map<StringPath, Object> parse(final String smp) {
        final Map<StringPath, Object> out = new HashMap<>();

        StringPath currentPath = new StringPath();
        boolean inStringLit = false;
        String s = "";
        for(int i = 0; i < smp.length(); i++) {
            if(smp.charAt(i) == ToSMP.PAIR_END) {
                out.put(currentPath, parsePrimitive(s));
                currentPath = currentPath.delete();
                s = "";
            } else if(smp.charAt(i) == ToSMP.ASSIGN) {
                currentPath = currentPath.append(s);
                s = "";
            }
            else if(smp.charAt(i) == ToSMP.QUOTE)
                inStringLit = !inStringLit;
            else if(inStringLit || (!Character.isWhitespace(smp.charAt(i)) && smp.charAt(i) != ToSMP.OBJ_START))
                s += smp.charAt(i);
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
        if(primitive.equals("" + OBJ_END))
            return new PlaceHolder();
        if(primitive.charAt(0) == ToSMP.CHAR_QUOTE && primitive.charAt(2) == ToSMP.CHAR_QUOTE)
            return primitive.charAt(1);
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
        return primitive;
    }

}
