package duorourou.restful.test.utils.comparator.response.body;

import com.fasterxml.jackson.databind.JsonNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.CompareResultType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class BodyComparator {

    public List<CompareResult> compare(JsonNode expect, JsonNode actual) {

        List<CompareResult> compareResult = newArrayList();
        Iterator<Map.Entry<String, JsonNode>> iterator = expect.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (actual.has(entry.getKey())) {
                compareJsonNode(compareResult, entry.getKey(), entry.getValue(), actual.get(entry.getKey()));
            } else {
                compareResult.add(CompareResult.build(entry.getKey(), null, null, CompareResultType.FIELD_NOT_EXIST));
            }
        }
        return compareResult;
    }

    private boolean compareValue(Object expect, Object actual) {
        return Objects.equals(expect, actual);
    }

    private void compareArrayValue(List<CompareResult> compareResult, String currentKey,
                                   JsonNode expect, JsonNode actual) {
        List<JsonNode> originList = newArrayList(expect.elements());
        List<JsonNode> destList = newArrayList(actual.elements());
        if (originList.size() != destList.size()) {
            compareResult.add(CompareResult.build(currentKey, expect, actual, CompareResultType.SIZE_NOT_EQUAL));
            return;
        }
        for (int index = 0; index < originList.size(); index++) {
            compareJsonNode(compareResult, currentKey + "[" + index + "]", originList.get(index), destList.get(index));
        }
    }

    private void compareJsonNode(List<CompareResult> compareResult, String currentKey, JsonNode expect, JsonNode actual) {
        if (expect.getNodeType() != actual.getNodeType()) {
            CompareResult.build(currentKey, expect, actual, CompareResultType.TYPE_NOT_EQUAL);
            return;
        }
        if (expect.isObject()) {
            expect.fields().forEachRemaining(entry -> compareJsonNode(compareResult,
                    currentKey + "." + entry.getKey(), entry.getValue(), actual.get(entry.getKey())));
        } else if (expect.isArray()) {
            compareArrayValue(compareResult, currentKey, expect, actual);
        } else if (!compareValue(expect.asText(), actual.asText())) {
            compareResult.add(CompareResult.build(currentKey, expect, actual, CompareResultType.VALUE_NOT_EQUAL));
        }
    }

}
