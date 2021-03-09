package com.xarql.util;

import static com.xarql.util.Math.max;
import static com.xarql.util.Math.min;
import java.util.Random;

/**
 * Represents a range. The range is inclusive for its start but exclusive for its end
 */
public final class Range implements Copier<Range> {

	public final int start;
	public final int end;

	public Range(final int point1, final int point2) {
		start = min(point1, point2);
		end = max(point1, point2);
	}

	public Range() {
		this(0, 0);
	}

	public static boolean verifyOrder(final int min, final int max) {
		return min <= max;
	}

	public Range withBound(final int bound) {
		return new Range(min(bound, start), max(bound, end));
	}

	public Range withStart(final int start) {
		return new Range(min(start, end), max(start, end));
	}

	public Range withFinish(final int finish) {
		return new Range(min(start, finish), max(finish, start));
	}

	public int size() {
		return end - start;
	}

	/**
	 * Determines if this range contains the input,
	 * including the start but excluding the end
	 *
	 * @param n A number
	 * @return if n is in range
	 */
	public boolean has(final int n) {
		return n >= start && n < end;
	}

	public boolean isEmpty() {
		return start == 0 && end == 0;
	}

	public float constrain(final float n) {
		if(n < start) {
			return start;
		} else if(n > end) {
			return end;
		} else {
			return n;
		}
	}

	public Range restrict(final Range r) {
		var output = r.copy().data;
		if(r.start < start) {
			output = output.withStart(start);
		}
		if(r.end > end) {
			output = output.withFinish(end);
		}
		return output;
	}

	public int random() {
		if(size() == 0) {
			return start;
		} else {
			return new Random().nextInt(size()) + start;
		}
	}

	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}

	@Override
	public boolean equals(final Object o) {
		if(!(o instanceof Range)) {
			return false;
		} else {
			final var r = (Range) o;
			return r.start == start && r.end == end;
		}
	}

	@Override
	public Copy<Range> copy() {
		final var r = new Range(start, end);
		return new Copy<>(this, r);
	}

	@Override
	public Range self() {
		return this;
	}

}
