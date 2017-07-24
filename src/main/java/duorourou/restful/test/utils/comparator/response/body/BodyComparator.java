package duorourou.restful.test.utils.comparator.response.body;

import com.fasterxml.jackson.databind.JsonNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.CompareResultType;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public abstract class BodyComparator {

    private static final String ROOT = "";

    public List<CompareResult> compare(JsonNode expect, JsonNode actual) {
        List<CompareResult> compareResult = newArrayList();
        if (expect.isArray() && actual.isArray()) {
            compareArrayValue(compareResult, ROOT, ROOT, expect, actual);
        } else if (expect.isObject() && actual.isObject()) {
            compareObject(expect, ROOT, actual, compareResult);
        } else {
            compareResult.add(CompareResult.build(ROOT, null, null, CompareResultType.FIELD_NOT_EXIST));
        }
        return compareResult;
    }

    private void compareObject(JsonNode expect, String currentPath,
                               JsonNode actual, List<CompareResult> compareResult) {
        Iterator<Map.Entry<String, JsonNode>> iterator = expect.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (actual.has(entry.getKey())) {
                compareJsonNode(compareResult, entry.getKey(),
                        getCurrentPath(currentPath, entry.getKey()),
                        entry.getValue(), actual.get(entry.getKey()));
            } else if (fieldCheckingAssertion(entry.getKey(), entry.getKey())) {
                compareResult.add(CompareResult.build(getCurrentPath(currentPath, entry.getKey()), null, null, CompareResultType.FIELD_NOT_EXIST));
            }
        }
    }

    private boolean compareValue(Object expect, Object actual) {
        return Objects.equals(expect, actual);
    }

    private void compareArrayValue(List<CompareResult> compareResult, String currentKey, String path,
                                   JsonNode expect, JsonNode actual) {
        List<JsonNode> originList = newArrayList(expect.elements());
        List<JsonNode> destList = newArrayList(actual.elements());
        if (originList.size() != destList.size()) {
            compareResult.add(CompareResult.build(currentKey, expect, actual, CompareResultType.SIZE_NOT_EQUAL));
            return;
        }
        for (int index = 0; index < originList.size(); index++) {
            String formattedIndex = formatIndex(index);
            compareJsonNode(compareResult, currentKey + formattedIndex,
                    path + formattedIndex, originList.get(index), destList.get(index));
        }
    }

    private void compareJsonNode(List<CompareResult> compareResult, String currentKey, String path,
                                 JsonNode expect, JsonNode actual) {
        if (!fieldCheckingAssertion(path, currentKey)) {
            return;
        }
        if (expect.getNodeType() != actual.getNodeType()) {
            compareResult.add(CompareResult.build(path, expect, actual, CompareResultType.TYPE_NOT_EQUAL));
            return;
        }
        compareValue(compareResult, currentKey, path, expect, actual);
    }

    private void compareValue(List<CompareResult> compareResult, String currentKey, String path, JsonNode expect, JsonNode actual) {
        if (expect.isObject()) {
            compareObject(expect, path, actual, compareResult);
//            expect.fields().forEachRemaining(entry -> compareJsonNode(compareResult,
//                    entry.getKey(), getCurrentPath(path, entry.getKey()), entry.getValue(), actual.get(entry.getKey())));
        } else if (expect.isArray()) {
            compareArrayValue(compareResult, currentKey, path, expect, actual);
        } else if (!compareValue(expect.asText(), actual.asText())) {
            compareResult.add(CompareResult.build(path, expect, actual, CompareResultType.VALUE_NOT_EQUAL));
        }
    }

    private String getCurrentPath(String parentPath, String currentField) {
        return parentPath + "." + currentField;
    }

    public abstract boolean fieldCheckingAssertion(String path, String fieldName);

    private String formatIndex(int index) {
        return MessageFormat.format("[{0}]", index);
    }

}
