package duorourou.restful.test.utils.comparator.response.status;

import com.fasterxml.jackson.databind.node.IntNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.NoneResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;

public class StatusComparator {

    private static final String STATUS = "status";

    public CompareResult compare(int expectStatus, int status) {
        return status == expectStatus
                ?
                NoneResult.instance() : ValueNotEqualResult.build(STATUS, new IntNode(expectStatus), new IntNode(status));
    }

}
