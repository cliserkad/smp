package xyz.cliserkad.util;

import java.util.Objects;

public class ElapseTimer {

	public static final long NANO_PER_MILLI = 1000000;
	public static final long NANO_PER_SEC = NANO_PER_MILLI * 1000;

	public final long start;

	/**
	 * Creates a record of the current system time in nanoseconds
	 */
	public ElapseTimer() {
		start = System.nanoTime();
	}

	/**
	 * Provides the typically desired output, which is the difference in nanoseconds between when this ElapseTimer was instantiated and now; the elapsed time.
	 *
	 * @return elapsed time
	 */
	public long out() {
		return System.nanoTime() - start;
	}

	/**
	 * Provides human readable output
	 *
	 * @return elapsed time
	 */
	@Override
	public String toString() {
		final var out = out();
		if(out / NANO_PER_SEC > 1) {
			return out / NANO_PER_SEC + " seconds";
		} else if(out / NANO_PER_MILLI > 1) {
			return out / NANO_PER_MILLI + " milliseconds";
		} else {
			return out + " nanoseconds";
		}
	}

	@Override
	public boolean equals(final Object object) {
		if(this == object) {
			return true;
		} else if(object instanceof final ElapseTimer that) {
			return start == that.start;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(start);
	}

}
