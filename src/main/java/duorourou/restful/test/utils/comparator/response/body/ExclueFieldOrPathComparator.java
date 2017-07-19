package duorourou.restful.test.utils.comparator.response.body;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ExclueFieldOrPathComparator {

    private final List<String> excludeFields = newArrayList();
    private final List<String> excludePaths = newArrayList();
}
