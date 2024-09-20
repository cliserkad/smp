package xyz.cliserkad.util;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

/**
 * Improved version of ArrayList
 */
public class BestList<E> extends ArrayList<E> {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(BestList.class);

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

	public static <E> BestList<E> nonNullList(final List<E> in) {
		if(in == null) {
			return new BestList<>();
		} else {
			return new BestList<>(in);
		}
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
		StringBuilder out = new StringBuilder();
		for(final E elm : this) {
			out.append(elm.toString().replace("\n", "")).append("\n");
		}
		return out.toString();
	}

	/**
	 * Concatenates toString() of every element in to one string without any separation
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
		StringBuilder out = new StringBuilder();
		for(final E elm : this) {
			out.append(elm.toString().replace(",", "")).append(",");
		}
		return out.toString();
	}

}
