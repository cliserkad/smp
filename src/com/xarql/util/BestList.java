package com.xarql.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Improved version of ArrayList that implements XML
 *
 * @param <E>
 * @author Bryan Johnson
 */
public class BestList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	@SafeVarargs
	public BestList(final E... elements) {
		super();
		add(elements);
	}

	public BestList(final Iterable<? extends E> elements) {
		super();
		add(elements);
	}

	@SafeVarargs
	public static <E> BestList<E> list(final E... element) {
		return new BestList<>(element);
	}

	// unchecked because parent add() function in ArrayList is also unchecked
	@SafeVarargs
	public final boolean add(final E... elements) {
		var output = true;
		for(final E e : elements) {
			if(!add(e)) {
				output = false;
			}
		}
		return output;
	}

	public final boolean add(final Iterable<? extends E> input) {
		var output = true;
		for(final E e : input) {
			if(!add(e)) {
				output = false;
			}
		}
		return output;
	}

	public final void removeAmount(final int amount) {
		for(var i = 0; i < amount; i++) {
			remove(0);
		}
	}

	public final E last() {
		return get(size() - 1);
	}

	/**
	 * Creates a string that holds the toString() of all of this list's elements
	 *
	 * @return combined toString()
	 */
	public String spread() {
		var out = "";
		for(final E elm : this) {
			out += elm.toString().replace("\n", "") + "\n";
		}
		return out;
	}

	/**
	 * Concatenates toString() of every element in to one string without any
	 * separation
	 *
	 * @return concat of toString() on every element
	 */
	public String squish() {
		final var builder = new StringBuilder();
		for(final E elm : this) {
			builder.append(elm.toString());
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		var out = "";
		for(final E elm : this) {
			out += elm.toString().replace(",", "") + ",";
		}
		return out;
	}

	public static <E> BestList<E> nonNullList(final List<E> in) {
		if(in == null) {
			return new BestList<>();
		} else {
			return new BestList<>(in);
		}
	}

}
