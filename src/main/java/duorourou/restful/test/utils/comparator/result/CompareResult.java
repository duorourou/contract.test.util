package duorourou.restful.test.utils.comparator.result;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.google.common.collect.Lists.newArrayList;

@Getter
@Setter
@NoArgsConstructor
public abstract class CompareResult<T> {

    public CompareResult(String fieldName) {
        this.fieldName = fieldName;
    }

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

    private static void buildForArrayNode() {

    }

}
