package test.java;

import com.xarql.util.Zipper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipperTest {

	public static final File TEST_DIR = new File(".github");
	public static final File TEST_OUTPUT_FILE = new File("target/test.zip");
	public static final int TEST_ZIP_SIZE = 1632;

	// test zipper by measuring size of output file
	@Test
	public void testZipperWithOutputPath() throws IOException {
		final File output = Zipper.zip(TEST_DIR, TEST_OUTPUT_FILE);
		final int zipLength = Files.readAllBytes(output.toPath()).length;
		assertEquals(TEST_ZIP_SIZE, zipLength);
	}

}
