package com.xarql.smp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public final class ParseUtil {
	public static final String NULL_MSG = " must not be null.";

	public static Map<Path, Object> parse(final File file) throws IOException {
		ensureNotNull(file, "File");
		return parse(file.toPath());
	}

	public static Map<Path, Object> parse(final java.nio.file.Path path) throws IOException {
		ensureNotNull(path, "Path");
		return GenericParser.parse(Files.readString(path));
	}

	public static Map<Path, Object> parseOrDefault(final File file, final String defaultData) {
		try {
			return parse(file);
		} catch(IOException | NullPointerException e) {
			return GenericParser.parse(defaultData);
		}
	}

	public static Map<Path, Object> parseOrDefault(final java.nio.file.Path path, final String defaultData) {
		try {
			return parse(path);
		} catch (IOException | NullPointerException e) {
			return GenericParser.parse(defaultData);
		}
	}

	public static void ensureNotNull(final Object o, final String type) {
		if(o == null)
			throw new NullPointerException(type + NULL_MSG);
	}

}
