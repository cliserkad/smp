package test.java;

public class CoverageRunner {

	public static void main(String[] args) throws Exception {
		new BaseConverterTest().testHighestInRangeTest();
		new BaseConverterTest().testLowestInRange();

		new EncodeTest().testCar();
		new MergeSortTest().testArray();
		new NestedListTest().testParse();
		new ParseTest().testCar();
		new ParseUtilTest().testCar0();
		new TemplateTest().testSimple();
		new ZipperTest().testZipperWithOutputPath();
	}

}
