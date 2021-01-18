package test.java;

import com.xarql.smp.ParseUtil;
import com.xarql.smp.Path;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParseUtilTest {

	public static final File CAR0 = new File("src/test/smp/car0.smp");

	public static void main(String[] args) {
		new ParseUtilTest().testCar0();
	}

	@Test
	public void testCar0() {
		try {
			final Map<Path, Object> data = ParseUtil.parse(CAR0);
			ParseTest.verifyData(data);
		} catch(Exception e) {
			assertNull(e);
		}
	}

}
