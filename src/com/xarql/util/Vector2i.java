package com.xarql.util;

import java.util.Objects;

public final class Vector2i implements Copier<Vector2i> {
    public final int x;
    public final int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i() {
        this(0, 0);
    }

    public Vector2i withX(int x) {
        return new Vector2i(x, this.y);
    }

    public Vector2i editX(int offset) {
        return editCoords(offset, 0);
    }

    public Vector2i withY(int y) {
        return new Vector2i(this.x, y);
    }

    public Vector2i editY(int offset) {
        return editCoords(0, offset);
    }

    public Vector2i editCoords(int xOffset, int yOffset) {
        return new Vector2i(x + xOffset, y + yOffset);
    }

    @Override
    public String toString() {
        return "x:" + x + " y:" + y;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        else if(o instanceof Vector2i) {
            Vector2i p = (Vector2i) o;
            return p.x == x && p.y == y;
        } else
            return false;
    }

    @Override
    public Copy<Vector2i> copy() {
        Vector2i p = new Vector2i(x, y);
        return new Copy<>(this, p);
    }

    @Override
    public Vector2i self() {
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
