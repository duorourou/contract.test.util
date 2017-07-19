package duorourou.restful.test.utils.comparator.result;

public class ArraySizeNotEqualResult extends CompareResult<Integer> {

    private ArraySizeNotEqualResult(String fieldName) {
        super(fieldName);
    }

    public static ArraySizeNotEqualResult build(String fieldName, int expect, int actual) {
        ArraySizeNotEqualResult result = new ArraySizeNotEqualResult(fieldName);
        result.setExpect(expect);
        result.setActual(actual);
        return result;
    }
}
