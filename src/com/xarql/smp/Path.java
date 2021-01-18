package com.xarql.smp;

import java.io.Serializable;
import java.util.Arrays;

public class Path implements Serializable {
    // use xxhsum 32 bit unsigned to generate a hash
    // xxhsum -H32 Path.java
    public static final long SERIAL_VERSION = 0xd83b1186;
    public static final char PATH_SEPARATOR = '/';

    private final String[] parts;

    public Path() {
        this(new String[0]);
    }

    public Path(String...parts) {
        if(isArrayEmpty(parts))
            this.parts = new String[0];
        else {
            for(int i = 0; i < parts.length; i++)
                if(parts[i].contains(PATH_SEPARATOR + ""))
                    throw new IllegalArgumentException("The part \"" + parts[i] + "\" @ " + i + " contains " + PATH_SEPARATOR);
            this.parts = parts;
        }
    }

    public Path copy() {
        return new Path(getParts());
    }

    public Path(String raw) {
        this(raw.split("" + PATH_SEPARATOR));
    }

    public String part(int index) {
        return parts[index];
    }

    public String first() {
        return parts[0];
    }

    public String last() {
        return parts[size() - 1];
    }

    public int size() {
        return parts.length;
    }

    public Path prepend(String s) {
        return prepend(new Path(s));
    }

    public Path prepend(String...parts) {
        checkArray(parts);
        return prepend(new Path(parts));
    }

    public Path prepend(Path p) {
        String[] newParts = getParts(p.size());
        for(int i = 0; i < p.size(); i++)
            newParts[i] = p.part(i);
        return new Path(newParts);
    }

    public Path append(String s) {
        return append(new Path(s));
    }

    public Path append(String...parts) {
        checkArray(parts);
        return append(new Path(parts));
    }

    public Path append(Path p) {
        String[] newParts = getParts(-p.size());
        for(int i = 0; i < p.size(); i++)
            newParts[i + size()] = p.part(i);
        return new Path(newParts);
    }

    public String[] getParts() {
        return getParts(0);
    }

    public String[] getParts(int offset) {
        return getParts(offset, size());
    }

    /**
     * Creates a copy of this StringPath's parts.
     *
     * @param offset Amount of null elements to prepend
     *
     */
    public String[] getParts(int offset, int amount) {
        final int diff = Math.abs(offset);
        offset = Math.max(0, offset);
        String[] parts = new String[amount + diff];
        System.arraycopy(this.parts, 0, parts, offset, amount);
        return parts;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < parts.length; i++) {
            builder.append(parts[i]);
            if(i < parts.length - 1)
                builder.append(PATH_SEPARATOR);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        else if(o instanceof Path) {
            Path that = (Path) o;
            return Arrays.equals(getParts(), that.getParts());
        } else if(o instanceof String) {
            Path that = new Path((String) o);
            return equals(that);
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getParts());
    }

    public Path delete() {
        return delete(1);
    }

    public Path delete(int amount) {
        return new Path(getParts(0, size() - amount));
    }

    public static boolean isArrayEmpty(String[] array) {
        return array == null || array.length == 0;
    }

    public static void checkArray(String[] array) {
        if(array == null)
            throw new IllegalArgumentException("String[] must not be null");
        if(array.length == 0)
            throw new IllegalArgumentException("String[] must not be empty");
    }

}
