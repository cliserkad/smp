package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSerialVersionUIDGenerator {

	@Test
	public void testUIDsAreUnique() {
		BestList<Class<?>> classesToTest = new BestList<>();

		classesToTest.add(BaseConverter.class);
		classesToTest.add(BestList.class);
		classesToTest.add(CheckedFunction.class);
		classesToTest.add(Copier.class);
		classesToTest.add(DiskLoc.class);
		classesToTest.add(Duo.class);
		classesToTest.add(ElapseTimer.class);
		classesToTest.add(ExceptionPack.class);
		classesToTest.add(Levenshtein.class);
		classesToTest.add(MergeSort.class);
		classesToTest.add(NamedObject.class);
		classesToTest.add(NamedObjectList.class);
		classesToTest.add(Path.class);
		classesToTest.add(PlaceHolder.class);
		classesToTest.add(Range.class);
		classesToTest.add(SerialCopier.class);
		classesToTest.add(SerialVersionUIDGenerator.class);
		classesToTest.add(Sizes.class);
		classesToTest.add(StringList.class);
		classesToTest.add(Text.class);
		classesToTest.add(TrackedMap.class);
		classesToTest.add(Trio.class);
		classesToTest.add(Tuple.class);
		classesToTest.add(TupleBase.class);
		classesToTest.add(UnimplementedException.class);
		classesToTest.add(Union.class);
		classesToTest.add(Union2.class);
		classesToTest.add(Union2Extendable.class);
		classesToTest.add(Union3.class);
		classesToTest.add(Union3Extendable.class);
		classesToTest.add(UnionMember.class);
		classesToTest.add(Vector2i.class);
		classesToTest.add(Zipper.class);

		Set<Long> serialUIDs = new HashSet<>();
		for(Class<?> clazz : classesToTest) {
			long serialUID = SerialVersionUIDGenerator.generateSerialVersionUID(clazz);
			assertTrue(serialUIDs.add(serialUID));
		}
		assertEquals(serialUIDs.size(), classesToTest.size());
	}

}
