package com.xarql.smp;

import java.util.Arrays;

public class StringPath {
    private final String[] parts;

    // I'm assuming the generated array is not
    // recoverable by the caller
    public StringPath(String...parts) {
        if(parts == null)
            this.parts = new String[0];
        else {
            for(int i = 0; i < parts.length; i++)
                if(parts[i].contains("/"))
                    throw new IllegalArgumentException("The part @ " + i + " (" + parts[i] + ") contains a forward slash");
            this.parts = parts;
        }
    }

    public StringPath() {
        this(null);
    }

    public StringPath copy() {
        return new StringPath(getParts());
    }

    public StringPath delete() {
        return delete(1);
    }

    public StringPath delete(int amt) {
        return new StringPath(getParts(parts.length - amt));
    }

    public StringPath append(String...parts) {
        final String[] combo = new String[parts.length + this.parts.length];
        for(int i = 0; i < combo.length; i++)
            if(i < this.parts.length)
                combo[i] = this.parts[i];
            else
                combo[i] = parts[i - this.parts.length];
        return new StringPath(combo);
    }

    public String[] getParts(int amt) {
        return Arrays.copyOf(parts, amt);
    }

    public String[] getParts() {
        return getParts(parts.length);
    }

    public String part(int index) {
        return parts[index];
    }

    public String first() {
        return part(0);
    }

    public String last() {
        return part(size() - 1);
    }

    public int size() {
        return parts.length;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(parts);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(obj instanceof StringPath) {
            final StringPath other = (StringPath) obj;
            // using the accessor will decrease performance somewhat,
            // but it's worth it for debugging / logging
            return Arrays.equals(other.getParts(), getParts());
        }
        else if(obj instanceof String) {
            final String string = (String) obj;
            final String[] parts = string.split("[/]");
            return equals(new StringPath(parts));
        }
        else
            return false;
    }

    @Override
    public String toString() {
        String out = "/";
        for(int i = 0; i < parts.length; i++) {
            out += parts[i];
            if(i < parts.length - 1)
                out += "/";
        }
        return out;
    }
}
