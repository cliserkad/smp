package test.java;

import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.xarql.smp.ParseUtil;
import com.xarql.util.Path;

public class ParseUtilTest {

	public static final File CAR0 = new File("src/test/smp/car0.smp");

	public static void main(final String[] args) {
		new ParseUtilTest().testCar0();
	}

	@Test
	public void testCar0() {
		try {
			final Map<Path, Object> data = ParseUtil.parse(CAR0);
			ParseTest.verifyData(data);
		} catch(final Exception e) {
			assertNull(e);
		}
	}

}
