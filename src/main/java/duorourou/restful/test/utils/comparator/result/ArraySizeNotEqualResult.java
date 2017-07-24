package duorourou.restful.test.utils.comparator.result;

import java.text.MessageFormat;

public class ArraySizeNotEqualResult extends CompareResult<Integer> {

    private ArraySizeNotEqualResult(String fieldName) {
        super(fieldName);
    }

    @Override
    public String toString() {
        return MessageFormat.format(LINE_SEPARATOR + "{0}[]" + LINE_SEPARATOR
                        + "\tExpected: size {1} " + LINE_SEPARATOR
                        + "\tactual : size {2} " + LINE_SEPARATOR
                , getFieldName(), getExpect(), getActual());
    }

    public static ArraySizeNotEqualResult build(String fieldName, int expect, int actual) {
        ArraySizeNotEqualResult result = new ArraySizeNotEqualResult(fieldName);
        result.setExpect(expect);
        result.setActual(actual);
        return result;
    }
}
