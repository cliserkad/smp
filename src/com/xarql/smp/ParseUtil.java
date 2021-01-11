package com.xarql.smp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class ParseUtil {
	public static final String NULL_MSG = " must not be null.";

	public static Map<StringPath, Object> parse(final File file) throws IOException {
		ensureNotNull(file, "File");
		return parse(file.toPath());
	}

	public static Map<StringPath, Object> parse(final Path path) throws IOException {
		ensureNotNull(path, "Path");
		return GenericParser.parse(Files.readString(path));
	}

	public static Map<StringPath, Object> parseOrDefault(final File file, final String defaultData) {
		try {
			return parse(file);
		} catch(IOException | NullPointerException e) {
			return GenericParser.parse(defaultData);
		}
	}

	public static Map<StringPath, Object> parseOrDefault(final Path path, final String defaultData) {
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
