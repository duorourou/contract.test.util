package duorourou.restful.test.utils.comparator.response.body;

import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class IncludeFieldOrPathComparator extends BodyComparator {
    private final String REGEX_PATH_INDEX = "\\[\\d+\\]";
    private final String EMPTY_STR = "";
    private final Set<String> includes = newHashSet();

    public IncludeFieldOrPathComparator includeFields(Collection<String> includeFields) {
        this.includes.addAll(includeFields);
        return this;
    }

    public IncludeFieldOrPathComparator includeFields(String... includeFields) {
        this.includes.addAll(newArrayList(includeFields));
        return this;
    }

    public IncludeFieldOrPathComparator includeField(String includeField) {
        this.includes.add(includeField);
        return this;
    }

    public IncludeFieldOrPathComparator includePath(String path) {
        return this;
    }

    public IncludeFieldOrPathComparator includePaths(Collection<String> paths) {
        return this;
    }

    public IncludeFieldOrPathComparator includePaths(String... paths) {
        return this;
    }

    @Override
    public boolean fieldCheckingAssertion(String currentPath, String currentFieldName) {
        return isIncludeField(currentFieldName) || isIncludePath(currentPath);
    }

    private String getPath(String path) {
        return path.replaceAll(REGEX_PATH_INDEX, EMPTY_STR);
    }

    private String getFieldName(String fieldName) {
        return fieldName.replaceAll(REGEX_PATH_INDEX, EMPTY_STR);
    }

    private boolean isIncludeField(String fieldName) {
        return includes.stream().anyMatch(include -> getFieldName(fieldName).startsWith(include));
    }

    private boolean isIncludePath(String path) {
        return includes.stream().anyMatch(include -> getPath(path).startsWith(include));
    }
}
