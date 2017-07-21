package duorourou.restful.test.utils.comparator.response.header;

import com.fasterxml.jackson.databind.JsonNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class HeaderComparator {

    List<CompareResult> compare(JsonNode actual, JsonNode expect) {

        List<CompareResult> compareResult = newArrayList();
        Iterator<Map.Entry<String, JsonNode>> iterator = expect.fields();

        return Collections.emptyList();
    }
}
