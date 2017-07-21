package duorourou.restful.test.utils.comparator.response.body;

import com.fasterxml.jackson.databind.JsonNode;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;
import duorourou.restful.test.utils.file.Reader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class IncludeFieldOrPathComparatorTest {
    private IncludeFieldOrPathComparator comparator;

    @Before
    public void setUp() {
        comparator = new IncludeFieldOrPathComparator();
    }

    @After
    public void tearDown() throws Exception {
        comparator = null;
    }

    @Test
    public void should_just_test_the_include_fields_when_include_fields_is_not_empty_and_include_path_is_empty() throws IOException {
        Reader reader = new Reader();

        JsonNode actual = reader.read("src/test/resources/json/include/array_value_not_same/actual.json");
        JsonNode expect = reader.read("src/test/resources/json/include/array_value_not_same/expect.json");

        comparator.includeFields("name");
        comparator.includeFields(newArrayList("list"));

        List<CompareResult> compareResults = comparator.compare(expect, actual);

        assertThat(compareResults).isNotEmpty().hasSize(1);
        Optional<CompareResult> result = compareResults.stream().findAny();

        assertThat(result).isPresent();
        assertThat((ValueNotEqualResult)result.get())
                .hasFieldOrPropertyWithValue("actual", "http://localhost:8080/test_data/1")
                .hasFieldOrPropertyWithValue("fieldName", "list[1].ref")
                .hasFieldOrPropertyWithValue("expect", "http://localhost:8999/test_data/1");
    }

    @Test
    public void should_just_test_the_include_path_when_include_path_is_not_empty_and_include_fields_is_empty() {

    }

}