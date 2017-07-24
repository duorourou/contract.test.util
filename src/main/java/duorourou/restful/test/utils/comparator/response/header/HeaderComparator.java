package duorourou.restful.test.utils.comparator.response.header;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.FieldNotExistResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class HeaderComparator {

    public List<CompareResult> compare(JsonNode expect, Map<String, String> headers) {
        if(expect == null) {
            return Collections.emptyList();
        }
        List<CompareResult> compareResult = newArrayList();
        Iterator<Map.Entry<String, JsonNode>> iterator = expect.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> expectHeader = iterator.next();
            if (!isExistedHeader(headers, expectHeader)) {
                compareResult.add(FieldNotExistResult.build(expectHeader.getKey()));
            } else if (!isSameHeader(headers, expectHeader)) {
                compareResult.add(ValueNotEqualResult.build(expectHeader.getKey(), expectHeader.getValue(),
                        new TextNode(headers.get(expectHeader.getKey()))));
            }
        }
        return compareResult;
    }

    private boolean isExistedHeader(Map<String, String> headers, Map.Entry<String, JsonNode> expectHeader) {
        return headers.keySet().contains(expectHeader.getKey());
    }

    private boolean isSameHeader(Map<String, String> headers, Map.Entry<String, JsonNode> expectHeader) {
        return (expectHeader.getValue() == null && headers.get(expectHeader.getKey()) == null)
                ||
                (expectHeader.getValue() != null
                        && expectHeader.getValue().asText().equals(headers.get(expectHeader.getKey())));
    }


}
