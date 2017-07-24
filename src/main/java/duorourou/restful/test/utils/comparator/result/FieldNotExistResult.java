package duorourou.restful.test.utils.comparator.result;

import java.text.MessageFormat;

public class FieldNotExistResult extends CompareResult<String> {

    private FieldNotExistResult(String fieldName) {
        super(fieldName);
    }

    @Override
    public String toString() {
        return MessageFormat.format(LINE_SEPARATOR + "{0}" + LINE_SEPARATOR
                        + "\tExpected:  {1} " + LINE_SEPARATOR
                        + "\tactual : none found. " + LINE_SEPARATOR,
                getFieldName(), getExpect(), getActual());
    }

    public static FieldNotExistResult build(String fieldName) {
        return new FieldNotExistResult(fieldName);
    }
}
