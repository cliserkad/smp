package com.xarql.util;

import java.io.File;

public class Math {

    public static int max(int... input) {
        if(input == null || input.length == 0)
            return 0;
        else if(input.length == 1)
            return input[0];
        else {
            int max = input[0];
            for(int i : input)
                if(i > max)
                    max = i;
            return max;
        }
    }

    public static int min(int... input) {
        if(input == null || input.length == 0)
            return 0;
        else if(input.length == 1)
            return input[0];
        else {
            int min = input[0];
            for(int i : input)
                if(i < min)
                    min = i;
            return min;
        }
    }

    public static int avg(int... input) {
        if(input == null || input.length == 0)
            return 0;

        int total = 0;
        for(int i : input)
            total += i;
        return total / input.length;
    }

    public static double avg_d(double... input) {
        if(input == null || input.length == 0)
            return 0;

        double total = 0;
        for(double d : input)
            total += d;
        return total / input.length;
    }

    /**
     * Custom "to the power of" method for determining exponential values. For
     * whatever reason Math.pow() doesn't work properly for certain use cases. The
     * output might suffer from an integer overflow if the arguments are too large.
     *
     * @param number The base number
     * @param power The exponent
     * @return number^power
     */
    public static long pow(int number, int power) {
        if(power == 0 || number == 0)
            return 1;
        long output = number;
        while(power > 1) {
            output *= number;
            power--;
        }
        return output;
    }

}
