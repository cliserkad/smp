package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.cliserkad.smp.GenericParser.parse;
import static xyz.cliserkad.smp.SimpleEncoder.encode;
import static xyz.cliserkad.smp.SimpleEncoder.prettyPrint;

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
