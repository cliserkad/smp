package com.xarql.util;

import java.util.Objects;

public final class Vector2i implements Copier<Vector2i> {

	public final int x;
	public final int y;

	public Vector2i(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i() {
		this(0, 0);
	}

	public Vector2i withX(final int x) {
		return new Vector2i(x, y);
	}

	public Vector2i editX(final int offset) {
		return editCoords(offset, 0);
	}

	public Vector2i withY(final int y) {
		return new Vector2i(x, y);
	}

	public Vector2i editY(final int offset) {
		return editCoords(0, offset);
	}

	public Vector2i editCoords(final int xOffset, final int yOffset) {
		return new Vector2i(x + xOffset, y + yOffset);
	}

	@Override
	public String toString() {
		return "x:" + x + " y:" + y;
	}

	@Override
	public boolean equals(final Object o) {
		if(o == this) {
			return true;
		} else if(o instanceof final Vector2i p) {
			return p.x == x && p.y == y;
		} else {
			return false;
		}
	}

	@Override
	public Copy<Vector2i> copy() {
		final var p = new Vector2i(x, y);
		return new Copy<>(this, p);
	}

	@Override
	public Vector2i self() {
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

}
