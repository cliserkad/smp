package com.xarql.util;

public class Text {

	public static final char[] PUNCTUATION_MARKS = { ':', '.', ',', ';', '?', '!', '"', '\'' };
	public static final char[] ESCAPE_CHARS = { '\n', '\t', '\r', '\f' }; // newline, tab, return, feed

	/** Start of standard ASCII typeable characters (inclusive) */
	public static final char START_OF_TYPEABLE = 32;
	/** End of standard ASCII typeable characters (exclusive) */
	public static final char END_OF_TYPEABLE = 127;
	/** Start of ASCII digits (inclusive) */
	public static final char START_OF_DIGITS = 48;
	/** End of ASCII digits (exclusive) */
	public static final char END_OF_DIGITS = 58;
	/** Start of ASCII Capitols (inclusive) */
	public static final char START_OF_CAPITOLS = 65;
	/** End of ASCII Capitols (exclusive) */
	public static final char END_OF_CAPITOLS = 91;
	/** Start of ASCII lowercase (inclusive) */
	public static final char START_OF_LOWERCASE = 97;
	/** End of ASCII lowercase (exclusive) */
	public static final char END_OF_LOWERCASE = 123;

	public static final int AMOUNT_OF_LETTERS = 26;
	public static final int AMOUNT_OF_DIGITS = 10;

	/**
	 * Determines if a character is in the range a-z
	 */
	public static boolean a_z(final char c) {
		return c >= START_OF_LOWERCASE && c < END_OF_LOWERCASE;
	}

	/**
	 * @see Text#a_z(char)
	 */
	public static boolean a_z(final String text) {
		for(var i = 0; i < text.length(); i++) {
			if(!a_z(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a character is in the range A-Z
	 */
	public static boolean A_Z(final char c) {
		return c >= START_OF_CAPITOLS && c < END_OF_CAPITOLS;
	}

	/**
	 * @see Text#A_Z(char)
	 */
	public static boolean A_Z(final String text) {
		for(var i = 0; i < text.length(); i++) {
			if(!A_Z(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a character is a standard typable character
	 */
	public static boolean isTypable(final char c) {
		return c >= START_OF_TYPEABLE && c < END_OF_TYPEABLE;
	}

	/**
	 * @see Text#isTypable(char)
	 */
	public static boolean isTypable(final String text) {
		for(var i = 0; i < text.length(); i++) {
			if(!isTypable(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a character has 7 or less significant bits. Therefore, if a
	 * character is part of ASCII.
	 */
	public static boolean isAscii(final char c) {
		return c < 128;
	}

	/**
	 * @see Text#isAscii(char)
	 */
	public static boolean isAscii(final String text) {
		for(var i = 0; i < text.length(); i++) {
			if(!isAscii(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a character has 8 or less significant bits. Therefore, if a
	 * character is part of extended ASCII.
	 */
	public static boolean isAsciiExtended(final char c) {
		return c <= Byte.MAX_VALUE;
	}

	/**
	 * @see Text#isAsciiExtended(char)
	 */
	public static boolean isAsciiExtended(final String text) {
		for(var i = 0; i < text.length(); i++) {
			if(!isAsciiExtended(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Only allow a-z and A-Z
	 *
	 * @param word
	 * @return true if all chars are in the latin alphabet
	 */
	public static boolean isLatinWord(final String word) {
		for(var i = 0; i < word.length(); i++) {
			if(!a_z(word.charAt(i)) && !A_Z(word.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * only allow a-z, A-Z and whitespace
	 *
	 * @param in A supposedly latin word
	 * @return true if all chars are latin
	 */
	public static boolean isLatin(final String in) {
		for(var i = 0; i < in.length(); i++) {
			if(!a_z(in.charAt(i)) && !A_Z(in.charAt(i)) && !Character.isWhitespace(in.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaNumeric(final String in) {
		for(var i = 0; i < in.length(); i++) {
			if(!a_z(in.charAt(i)) || !A_Z(in.charAt(i)) || !isDigit(in.charAt(i)))
				return false;
		}
		return true;
	}

	private static boolean isDigit(char c) {
		return c >= START_OF_DIGITS && c < END_OF_DIGITS;
	}

	public static boolean isEmpty(final String s) {
		return s == null || s.length() == 0;
	}

	public static String checkNotEmpty(final String s) {
		if(isEmpty(s)) {
			throw new IllegalArgumentException("Provided string may not be null nor empty");
		}
		return s;
	}

	public static String nonNull(final String s) {
		if(s == null) {
			return "";
		} else {
			return s;
		}
	}

	public static boolean isPunctuation(final char c) {
		for(final char mark : PUNCTUATION_MARKS) {
			if(c == mark) {
				return true;
			}
		}
		return false;
	}

	public static String removePunctuation(final String s) {
		var output = "";
		for(var i = 0; i < s.length(); i++) {
			if(!isPunctuation(s.charAt(i))) {
				output += s.charAt(i);
			}
		}
		return output;
	}

	public static String larger(final String preferred, final String other) {
		if(other == null || other.length() <= preferred.length()) {
			return preferred;
		} else {
			return other;
		}
	}

	public static String largest(final String... input) {
		// null protection
		if(input == null || input.length == 0) {
			return "";
		} else {
			var output = input[0];
			for(final String s : input) {
				output = larger(output, s);
			}
			return output;
		}
	}

	public static String largest(final Iterable<String> input) {
		// null protection
		if(input == null) {
			return "";
		} else {
			String output = null;
			for(final String s : input) {
				output = larger(s, output);
			}
			return output;
		}
	}

	public static String smaller(final String preferred, final String other) {
		if(other == null || other.length() >= preferred.length()) {
			return preferred;
		} else {
			return other;
		}
	}

	public static String smallest(final String... input) {
		// null protection
		if(input == null || input.length == 0) {
			return "";
		} else {
			var output = input[0];
			for(final String s : input) {
				output = smaller(output, s);
			}
			return output;
		}
	}

	public static String smallest(final Iterable<String> input) {
		// null protection
		if(input == null) {
			return "";
		} else {
			String output = null;
			for(final String s : input) {
				output = larger(output, s);
			}
			return output;
		}
	}

	/**
	 * Turns CamelCaseNames in to lower_case_names
	 *
	 * @param input CamelCase String
	 * @return lower_case_string
	 */
	public static String undoCamelCase(final String input) {
		var output = "";
		for(var i = 0; i < input.length(); i++) {
			if(!Character.isAlphabetic(input.charAt(i)) && input.charAt(i) != ';') {
				output += "-";
			}
			if(i == 0) {
				output += (input.charAt(0) + "").toLowerCase();
			} else {
				if(Character.isUpperCase(input.charAt(i))) {
					output += "_" + (input.charAt(i) + "").toLowerCase();
				} else {
					output += "" + input.charAt(i);
				}
			}
		}
		return output;
	}

	public static boolean isFirstLetterUppercase(final String in) {
		return !isEmpty(in) && Character.isUpperCase(in.charAt(0));
	}

	public static boolean isFirstLetterLowercase(final String in) {
		return !isEmpty(in) && Character.isLowerCase(in.charAt(0));
	}

	public static boolean hasUppercase(final String in) {
		if(isEmpty(in)) {
			return false;
		} else {
			for(var i = 0; i < in.length(); i++) {
				if(Character.isUpperCase(in.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean hasLowercase(final String in) {
		if(isEmpty(in)) {
			return false;
		} else {
			for(var i = 0; i < in.length(); i++) {
				if(Character.isLowerCase(in.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}

}
