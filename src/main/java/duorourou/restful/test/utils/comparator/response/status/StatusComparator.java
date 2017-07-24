package duorourou.restful.test.utils.comparator.response.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;

public class StatusComparator {

    private static final String STATUS = "status";

    public CompareResult compare(JsonNode expect, int status) {
        return status == expect.asInt()
                ?
                null : ValueNotEqualResult.build(STATUS, expect, new IntNode(status));
    }

}
