package test.java;

import static com.xarql.smp.GenericParser.parse;
import static com.xarql.smp.SimpleEncoder.encode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.xarql.smp.Path;

public class ParseTest {

	public static final int EXPECTED_YEAR = 1998;
	public static final String EXPECTED_MODEL = "Honda Accord";
	public static final char EXPECTED_TYPE = 'c';
	public static final int EXPECTED_ENGINE_CYLINDERS = 6;
	public static final String EXPECTED_ENGINE_NAME = "VTEC";
	public static final float EXPECTED_ENGINE_MPG = 14.834f;
	public static final char[] EXPECTED_DRIVE_MODES = { 'p', 'r', 'n', 'd', 'l' };

	public static void main(final String[] args) throws IllegalAccessException {
		new ParseTest().testCar();
	}

	public static void verifyData(final Map<Path, Object> data) {
		assertEquals(EXPECTED_YEAR, data.get(new Path("year")));
		assertEquals(EXPECTED_MODEL, data.get(new Path("model")));
		assertEquals(EXPECTED_TYPE, data.get(new Path("type")));
		assertEquals(EXPECTED_ENGINE_CYLINDERS, data.get(new Path("engine", "cylinders")));
		assertEquals(EXPECTED_ENGINE_NAME, data.get(new Path("engine", "name")));
		assertEquals(EXPECTED_ENGINE_MPG, data.get(new Path("engine", "mpg")));
		for(var i = 0; i < EXPECTED_DRIVE_MODES.length; i++) {
			assertEquals(EXPECTED_DRIVE_MODES[i], data.get(new Path("driveModes", "" + i)));
		}
	}

	@Test
	public void testCar() throws IllegalAccessException {
		verifyData(parse(encode(new Car())));
	}

}
