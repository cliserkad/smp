package com.xarql.util;

import java.io.File;
import com.xarql.smp.SimpleEncoder;

/**
 * Class that makes Files easier to work with
 *
 * @author Bryan Johnson
 */
public class DiskLoc extends File implements Copier<DiskLoc> {

	public static final String DOT = ".";
	private static final long serialVersionUID = 1L;

	// Not sure if forward slash is 100% foolproof
	public DiskLoc(final String... pathParts) {
		super(String.join("/", pathParts));
	}

	public DiskLoc(final File f) {
		super(f.getAbsolutePath());
	}

	public static boolean hasExtension(final File f) {
		return f.getName().contains(".");
	}

	public boolean hasExtension() {
		return hasExtension(this);
	}

	public static String extensionOf(final File f) {
		if(!hasExtension(f)) {
			throw new IllegalArgumentException("This DiskLoc doesn't have an extension to be derived");
		}
		return f.getName().substring(f.getName().lastIndexOf(DOT) + 1);
	}

	/**
	 * Derives the extension (without '.') from this DiskLoc
	 * 
	 * @return file's extension
	 */
	public String extension() {
		return extensionOf(this);
	}

	public static String dotExtension(final File f) {
		return DOT + extensionOf(f);
	}

	public String unixName() {
		return getName().trim().replace(' ', '_').toLowerCase();
	}

	public DiskLoc unix() {
		return new DiskLoc(getParent(), unixName());
	}

	public DiskLoc withName(final String name) {
		return new DiskLoc(getParent(), name);
	}

	@Override
	public boolean delete() {
		return delete(this);
	}

	public static boolean delete(final File dir) {
		if(dir.isDirectory()) {
			var out = true;
			final var children = dir.listFiles();
			for(var i = 0; children != null && i < children.length; i++) {
				if(!delete(children[i])) {
					out = false;
				}
			}
			return out;
		} else {
			return dir.delete();
		}
	}

	@Override
	public boolean mkdir() {
		return mkdirs();
	}

	@Override
	public String toString() {
		return SimpleEncoder.prettyEncode(this);
	}

	@Override
	public Copy<DiskLoc> copy() {
		final var dl = new DiskLoc(getPath());
		return new Copy<>(this, dl);
	}

	@Override
	public DiskLoc self() {
		return this;
	}

}
