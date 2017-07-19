package duorourou.restful.test.utils.comparator.response.body;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class IncludeFieldOrPathComparator extends BodyComparator {
    private final List<String> includeFields = newArrayList();
    private final List<String> includePaths = newArrayList();

    public IncludeFieldOrPathComparator includeField() {

        return this;
    }

    public IncludeFieldOrPathComparator includePath() {
        return this;
    }

    @Override
    public boolean fieldCheckingAssertion(String currentPath, String currentFieldName) {
        return false;
    }
}
