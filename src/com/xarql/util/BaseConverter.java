package com.xarql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.xarql.util.Math.pow;
import static com.xarql.util.Text.*;

/**
 * Converts numbers to strings and vice versa. Supports bases of 2 to 64. Final
 * strings are probably non-standard and unusable in other programs, but the
 * layout is closer to ASCII than standard. Runs in <100 milliseconds on an
 * Apple M1 Pro.
 */
public class BaseConverter {

	public static final int MIN_SUPPORTED_BASE = 2;
	public static final int MAX_SUPPORTED_BASE = 64;
	public static final int FIRST_RUN_OFFSET = START_OF_DIGITS;
	public static final int SECOND_RUN_OFFSET = START_OF_CAPITOLS - AMOUNT_OF_DIGITS;
	public static final int THIRD_RUN_OFFSET = START_OF_LOWERCASE - (AMOUNT_OF_DIGITS + AMOUNT_OF_LETTERS);

	public static final String UNSUPPORTED_BASE = " is not within " + MIN_SUPPORTED_BASE + " & " + MAX_SUPPORTED_BASE;

	private static Map<Integer, Character> charValues = new HashMap<>();

	public static void main(final String[] args) {
		System.out.println("Checking conversions...");
		// checks that converting back and forth is non volatile in every supported base
		final var et = new ElapseTimer();
		final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for(var a = 2; a <= MAX_SUPPORTED_BASE; a++) {
			final int base = a;
			threadPool.execute(() -> {
				// test the highest 1024 numbers in the supported range
				final int max = Integer.MAX_VALUE;
				for (int num = max - 1024; num < max; num++) {
					if (num != toNumber(toString(num, base), base)) { // convert the number to a string, then back to a number
						System.err.println("Failed to convert " + num + " to base " + base + " and back again.");
					}
				}

				// test the lowest 1024 numbers in the supported range
				for (int num = 0; num < 1024; num++) {
					if (num != toNumber(toString(num, base), base)) { // convert the number to a string, then back to a number
						System.err.println("Failed to convert " + num + " to base " + base + " and back again.");
					}
				}

				System.out.println("Tested base " + base + "...");
			});
		}
		threadPool.shutdown();
		try {
			if(!threadPool.awaitTermination(10, TimeUnit.MINUTES))
				System.err.println("thread pool took too long to terminate");
		} catch(InterruptedException e) {
			System.err.println("thread pool was interrupted");
			e.printStackTrace();
		}
		System.out.println("yay! we checked that all in " + et);
	}

	public static void checkBase(final int base) {
		if(base > MAX_SUPPORTED_BASE || base < MIN_SUPPORTED_BASE) {
			throw new IllegalArgumentException(base + UNSUPPORTED_BASE);
		}
	}

	/**
	 * Converts a base 64 number (represented as a String) to a base 10 number
	 * (represented as an int). Example: E5; 873
	 *
	 * @param input String representing a base 64 number
	 * @return An int equal to the input
	 */
	public static int toNumber(final String input) {
		return toNumber(input, MAX_SUPPORTED_BASE);
	}

	/**
	 * Converts a String representing a number in the specified base to an int.
	 * Can convert from Binary, Octal, Hex, or even Base64.
	 * For use in conjunction with toString to convert numbers back and forth.
	 *
	 * @param input String representing a number
	 * @param base  base of number in input; [0, 64]
	 * @return An int
	 * @see BaseConverter#toString(int, int)
	 */
	public static int toNumber(final String input, final int base) {
		checkBase(base);
		var output = 0;
		for (var i = 0; i < input.length(); i++) {
			int charValue;
			if (input.charAt(i) >= START_OF_DIGITS && input.charAt(i) < END_OF_DIGITS) {
				charValue = input.charAt(i) - FIRST_RUN_OFFSET;
			} else if (input.charAt(i) >= START_OF_CAPITOLS && input.charAt(i) < END_OF_CAPITOLS) {
				charValue = input.charAt(i) - SECOND_RUN_OFFSET;
			} else if (input.charAt(i) >= START_OF_LOWERCASE && input.charAt(i) < END_OF_LOWERCASE) {
				charValue = input.charAt(i) - THIRD_RUN_OFFSET;
			} else if(input.charAt(i) == '-') {
				charValue = 62;
			} else if(input.charAt(i) == '_') {
				charValue = 63;
			} else {
				throw new IllegalArgumentException("Illegal Character : " + input.charAt(i));
			}
			final var weight = input.length() - 1 - i;
			output += charValue * pow(base, weight);
		}
		return output;
	}

	/**
	 * Constructs the Map that holds the values for each valid char in a Base64 number String
	 */
	private static void buildCharacterMap() {
		final var builtValues = new HashMap<Integer, Character>();
		var i = 0;
		int booster = START_OF_DIGITS;
		while(i < MAX_SUPPORTED_BASE) {
			if(i == AMOUNT_OF_DIGITS) {
				booster = SECOND_RUN_OFFSET;
			} else if(i == AMOUNT_OF_DIGITS + AMOUNT_OF_LETTERS) {
				booster = THIRD_RUN_OFFSET;
			} else if(i == 62) {
				booster = '-' - 62;
			} else if(i == 63) {
				booster = '_' - 63;
			}
			builtValues.put(i, (char) (i + booster));
			i++;
		}
		charValues = builtValues;
	}

	/**
	 * @return built charValues
	 */
	public static Map<Integer, Character> getCharValues() {
		if (charValues.isEmpty()) {
			buildCharacterMap();
		}
		return charValues;
	}

	/**
	 * Converts an input number to a string of the desired base.
	 * Can convert to Binary, Octal, Hex, or even Base64.
	 *
	 * @param input any int >= 0
	 * @param base  [2, 64]
	 * @return A String representing the input in the requested base
	 */
	public static String toString(int input, final int base) {
		checkBase(base);
		final StringBuilder output = new StringBuilder();
		final var digits = new ArrayList<Integer>();
		do {
			digits.add(input % base);
			input = (input - input % base) / base;
		} while (input > 0);

		for (var i = digits.size() - 1; i >= 0; i--) {
			output.append(getCharValues().get(digits.get(i)));
		}
		return output.toString();
	}

	/**
	 * Converts a base 10 number (represented as an int) to a Base64 number
	 * (represented as an String). Example: 873; E5
	 *
	 * @param input any int >= 0
	 * @return A String equal to the input
	 */
	public static String toString(final int input) {
		return toString(input, MAX_SUPPORTED_BASE);
	}

}
