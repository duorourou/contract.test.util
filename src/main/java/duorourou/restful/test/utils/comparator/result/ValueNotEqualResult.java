package duorourou.restful.test.utils.comparator.result;

import com.fasterxml.jackson.databind.JsonNode;

public class ValueNotEqualResult extends CompareResult<String> {

    private ValueNotEqualResult(String fieldName) {
        super(fieldName);
    }


    public static ValueNotEqualResult build(String fieldName, JsonNode expect, JsonNode actual) {
        ValueNotEqualResult result = new ValueNotEqualResult(fieldName);
        result.setActual(actual.asText());
        result.setExpect(expect.asText());
        return result;
    }
}
