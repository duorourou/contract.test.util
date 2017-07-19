package duorourou.restful.test.utils.comparator.result;

import com.fasterxml.jackson.databind.JsonNode;

public class TypeNotEqualResult extends CompareResult<String> {

    private TypeNotEqualResult(String fieldName) {
        super(fieldName);
    }

    public static TypeNotEqualResult build(String fieldName, JsonNode expect, JsonNode actual) {
        TypeNotEqualResult result = new TypeNotEqualResult(fieldName);
        result.setActual(actual.getNodeType().name());
        result.setExpect(expect.getNodeType().name());
        return result;
    }
}
