package test.java;

import static com.xarql.smp.GenericParser.parse;
import static com.xarql.smp.SimpleEncoder.encode;
import static com.xarql.smp.SimpleEncoder.prettyPrint;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.xarql.util.Path;

public class NestedListTest {

	public static void main(final String[] args) throws IllegalAccessException {
		System.out.println(prettyPrint(encode(new Squad())));
		new NestedListTest().testParse();
	}

	@Test
	public void testParse() throws IllegalAccessException {
		final var data = parse(encode(new Squad()));
		final var parentList = new Path("members");

		assertEquals("Smith", data.get(parentList.append("0", "0")));
		assertEquals("Charles", data.get(parentList.append("0", "1")));
		assertEquals("Roberts", data.get(parentList.append("0", "2")));

		assertEquals("Brown", data.get(parentList.append("1", "0")));
		assertEquals("Johnson", data.get(parentList.append("1", "1")));
		assertEquals("Romero", data.get(parentList.append("1", "2")));

		assertEquals("Freud", data.get(parentList.append("2", "0")));
		assertEquals("Tesla", data.get(parentList.append("2", "1")));
		assertEquals("McCain", data.get(parentList.append("2", "2")));

	}

}
