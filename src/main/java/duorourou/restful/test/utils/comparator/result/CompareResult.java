package duorourou.restful.test.utils.comparator.result;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;

import static com.google.common.collect.Lists.newArrayList;

@Getter
@Setter
@NoArgsConstructor
public abstract class CompareResult<T> {

    public CompareResult(String fieldName) {
        this.fieldName = fieldName;
    }

    public static final String LINE_SEPARATOR = System.lineSeparator();

    private String fieldName;
    private T expect;
    private T actual;

    public static CompareResult build(String fieldName, JsonNode expect, JsonNode actual, CompareResultType type) {
        switch (type) {
            case FIELD_NOT_EXIST:
                return FieldNotExistResult.build(fieldName);
            case TYPE_NOT_EQUAL:
                return TypeNotEqualResult.build(fieldName, expect, actual);
            case SIZE_NOT_EQUAL:
                return ArraySizeNotEqualResult.build(fieldName, newArrayList(expect.elements()).size(),
                        newArrayList(actual.elements()).size());
            case VALUE_NOT_EQUAL:
                return ValueNotEqualResult.build(fieldName, expect, actual);
            default:
                return new DefaultCompareResult();
        }
    }

    public boolean isNone() {
        return fieldName == null;
    }

    public boolean isNotNone() {
        return fieldName != null;
    }

    public String toString() {
        return MessageFormat.format(LINE_SEPARATOR + "{0}" + LINE_SEPARATOR
                        + "\tExpected:  {1} " + LINE_SEPARATOR
                        + "\tactual : {2} " + LINE_SEPARATOR
                , getFieldName(), getExpect(), getActual());
    }
}
