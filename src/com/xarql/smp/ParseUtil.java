package com.xarql.smp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public final class ParseUtil {
	public static final String NULL_MSG = " must not be null.";

	public static PathMap<Object> parse(final File file) {
		ensureNotNull(file, "File");
		return parse(file.toPath());
	}

	public static PathMap<Object> parse(final java.nio.file.Path path) {
		ensureNotNull(path, "Path");
		try {
			return GenericParser.parse(Files.readString(path));
		} catch(IOException e) {
			if(path.toFile().exists()) {
				e.printStackTrace();
			} else {
				try {
					path.getParent().toFile().mkdirs();
					path.toFile().createNewFile();
				} catch(IOException ignored) {
				}
			}
			return new PathMap<>();
		}
	}

	public static void ensureNotNull(final Object o, final String type) {
		if(o == null)
			throw new NullPointerException(type + NULL_MSG);
	}

}
