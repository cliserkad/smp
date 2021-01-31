package com.xarql.util;

import static com.xarql.util.Math.max;
import static com.xarql.util.Math.min;
import java.util.Random;

public final class Range implements Copier<Range> {
    public final int start;
    public final int finish;

    public Range(int point1, int point2) {
        start = min(point1, point2);
        finish = max(point1, point2);
    }

    public Range() {
        this(0, 0);
    }

    public static boolean verifyOrder(int min, int max) {
        return min <= max;
    }

    public Range withBound(int bound) {
        return new Range(min(bound, start), max(bound, finish));
    }

    public Range withStart(int start) {
        return new Range(min(start, finish), max(start, finish));
    }

    public Range withFinish(int finish) {
        return new Range(min(start, finish), max(finish, start));
    }

    public int size() {
        return finish - start;
    }

    /**
     * Inclusive contains
     * 
     * @param n A number
     * @return if n is in range
     */
    public boolean has(int n) {
        return n >= start && n <= finish;
    }

    public boolean isEmpty() {
        return start == 0 && finish == 0;
    }

    public float constrain(float n) {
        if(n < start)
            return start;
        else if(n > finish)
            return finish;
        else
            return n;
    }

    public Range restrict(Range r) {
        Range output = r.copy().data;
        if(r.start < start)
            output = output.withStart(start);
        if(r.finish > finish)
            output = output.withFinish(finish);
        return output;
    }

    public int random() {
        if(size() == 0)
            return 0 + start;
        else
            return new Random().nextInt(size()) + start;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + finish + "]";
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Range == false)
            return false;
        else {
            Range r = (Range) o;
            return r.start == start && r.finish == finish;
        }
    }

    @Override
    public Copy<Range> copy() {
        Range r = new Range(start, finish);
        return new Copy<Range>(this, r);
    }

    @Override
    public Range self() {
        return this;
    }
}
