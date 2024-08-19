package xyz.cliserkad.util;

import static java.lang.Math.abs;
import static xyz.cliserkad.util.Math.min;
import static xyz.cliserkad.util.Text.isEmpty;
import static xyz.cliserkad.util.Text.nonNull;

public class Levenshtein {

	public static final int DEFAULT_NEARNESS = 8;
	public static final int NO_LIMIT = -1;

	public static int distance(final String a, final String b) {
		return distance(a, b, NO_LIMIT);
	}

	public static boolean isNear(final String a, final String b, final int limit) {
		return distance(a, b, limit) <= limit;
	}

	public static boolean isNear(final String a, final String b) {
		return isNear(a, b, DEFAULT_NEARNESS);
	}

	public static int distance(String a, String b, final int limit) {
		if(isEmpty(a) && isEmpty(b)) {
			return 0;
		}

		a = nonNull(a);
		b = nonNull(b);

		if(a.equals(b)) {
			return 0;
		}

		if(limit >= 0 && abs(a.length() - b.length()) > limit) {
			return limit + 1;
		}
		if(a.length() == 0) {
			return b.length();
		}
		if(b.length() == 0) {
			return a.length();
		}

		// swap if a is smaller than b
		if(a.length() < b.length()) {
			final var tmp = a;
			a = b;
			b = tmp;
		}

		final var cost = new int[b.length() + 1];
		for(var i = 1; i <= a.length(); i += 1) {
			cost[0] = i;
			var prv = i - 1;
			var min = prv;
			for(var i2 = 1; i2 <= b.length(); i2 += 1) {
				final var act = prv + (a.charAt(i - 1) == b.charAt(i2 - 1) ? 0 : 1);
				cost[i2] = min(1 + (prv = cost[i2]), 1 + cost[i2 - 1], act);
				if(prv < min) {
					min = prv;
				}
			}
			if(limit >= 0 && min > limit) {
				return limit + 1;
			}
		}
		if(limit >= 0 && cost[b.length()] > limit) {
			return limit + 1;
		}
		return cost[b.length()];
	}

}
