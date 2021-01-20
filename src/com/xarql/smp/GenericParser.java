package com.xarql.smp;

import test.java.Car;

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
            Map<Path, Object> data = parse(smp);
            System.out.println(data.get(new Path("year")));
            System.out.println(data.get(new Path("model")));
            System.out.println(data.get(new Path("driveModes")));
        }
        final String smp2 = encode(new SimpleEncoder());
        System.out.println(prettyPrint(smp2));
        Map<Path, Object> data2 = parse(smp2);
        System.out.println(data2.get(new Path("CHAR_QUOTE")));

        System.out.println();
        System.out.println();

        System.out.println(prettyPrint(encode(new Car())));
    }

    public static PathMap<Object> parse(String smp) {
        return parse(new Path(), smp);
    }

    /**
     * Creates a Map which contains the data from an smp encoded string.
     * This makes smp values queryable in an easy to understand manner.
     *
     * @param smp A string representing any data encoded in smp
     * @return a Map with keys and values from smp
     */
    public static PathMap<Object> parse(Path currentPath, String smp) {
        final PathMap<Object> out = new PathMap<>();

        boolean inStringLit = false;
        boolean inCharLit = false;
        StringBuilder builder = new StringBuilder();
        for(int a = 0; a < smp.length(); a++) {
            if(smp.charAt(a) == BACKSLASH) {
                a++;
                builder.append(smp.charAt(a));
            } else {
                if(smp.charAt(a) == CHAR_QUOTE && !inStringLit)
                    inCharLit = !inCharLit;
                else if(smp.charAt(a) == QUOTE && !inCharLit)
                    inStringLit = !inStringLit;
                if(!inCharLit && !inStringLit) {
                    if(smp.charAt(a) == ASSIGN) {
                        currentPath = currentPath.append(builder.toString());
                        builder.setLength(0);
                    } else if(smp.charAt(a) == SEPARATOR || smp.charAt(a) == LIST_END) {
                        out.putAll(parse(currentPath.delete(), currentPath.last() + ":" + builder.toString() + ";"));
                        builder.setLength(0);
                        int depth = Integer.parseInt(currentPath.last());
                        currentPath = currentPath.delete().append(depth + 1 + "");
                        if(smp.charAt(a) == LIST_END) {
                            final int listSize = Integer.parseInt(currentPath.last());
                            final Object[] array = new Object[listSize];
                            for(int b = 0; b < listSize; b++)
                                array[b] = out.get(currentPath.delete().append(b + ""));
                            currentPath = currentPath.delete();
                            out.put(currentPath, array);
                        }
                    } else if(smp.charAt(a) == PAIR_END) {
                        if(out.get(currentPath) == null)
                            out.put(currentPath.copy(), parsePrimitive(builder.toString()));
                        builder.setLength(0);
                        currentPath = currentPath.delete();
                    } else if(smp.charAt(a) == LIST_START) {
                        currentPath = currentPath.append("" + 0);
                    } else if(!Character.isWhitespace(smp.charAt(a)) && smp.charAt(a) != OBJ_START)
                        builder.append(smp.charAt(a));
                }
                else
                    builder.append(smp.charAt(a));
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
        if(primitive.trim().equalsIgnoreCase(TRUE))
            return true;
        else if(primitive.trim().equalsIgnoreCase(FALSE))
            return false;
        else {
            try {
                return Integer.parseInt(primitive);
            } catch (NumberFormatException e) {
                // do nothing
            }
            try {
                return Float.parseFloat(primitive);
            } catch (NumberFormatException e) {
                // do nothing
            }
            try {
                return Long.parseLong(primitive);
            } catch (NumberFormatException e) {
                // do nothing
            }
            try {
                return Double.parseDouble(primitive);
            } catch (NumberFormatException e) {
                // do nothing
            }
            return primitive.substring(1, primitive.length() - 1);
        }
    }

}
