package xyz.cliserkad.util;

import java.util.Iterator;
import java.util.Random;

import static xyz.cliserkad.util.Math.max;
import static xyz.cliserkad.util.Math.min;

/**
 * Represents a range. The range is inclusive for its minimum but exclusive for its maximum.
 */
public final class Range implements Copier<Range>, Iterable<Integer> {

	public final int min;
	public final int max;

	public Range(final int min, final int max) {
		this.min = min(min, max);
		this.max = max(min, max);
	}

	public Range() {
		this(0, 0);
	}

	public static boolean verifyOrder(final int min, final int max) {
		return min <= max;
	}

	public Range withBound(final int bound) {
		return new Range(min(bound, min), max(bound, max));
	}

	public Range withMin(final int min) {
		return new Range(min(min, max), max(min, max));
	}

	public Range withMax(final int max) {
		return new Range(min(min, max), max(max, min));
	}

	public int size() {
		return max - min;
	}

	/**
	 * Determines if this range contains the input, including the start but excluding the end
	 *
	 * @param n The number to check
	 * @return n >= min && n < max
	 */
	public boolean has(final int n) {
		return n >= min && n < max;
	}

	public boolean isEmpty() {
		return min == 0 && max == 0;
	}

	public float constrain(final float n) {
		if(n < min) {
			return min;
		} else if(n > max) {
			return max;
		} else {
			return n;
		}
	}

	public Range restrict(final Range r) {
		var output = r.copy().data;
		if(r.min < min) {
			output = output.withMin(min);
		}
		if(r.max > max) {
			output = output.withMax(max);
		}
		return output;
	}

	public int random() {
		if(size() == 0) {
			return min;
		} else {
			return new Random().nextInt(size()) + min;
		}
	}

	@Override
	public String toString() {
		return "[" + min + ", " + max + "]";
	}

	@Override
	public boolean equals(final Object o) {
		if(!(o instanceof final Range r)) {
			return false;
		} else {
			return r.min == min && r.max == max;
		}
	}

	@Override
	public Copy<Range> copy() {
		final var r = new Range(min, max);
		return new Copy<>(this, r);
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<>() {

			private int currentIndex = min;

			@Override
			public boolean hasNext() {
				return currentIndex < max;
			}

			@Override
			public Integer next() {
				return currentIndex++;
			}
		};
	}

}
