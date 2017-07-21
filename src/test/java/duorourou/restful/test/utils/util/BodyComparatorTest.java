package duorourou.restful.test.utils.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duorourou.restful.test.utils.comparator.response.body.BodyComparator;
import duorourou.restful.test.utils.comparator.response.body.DefaultBodyComparator;
import duorourou.restful.test.utils.comparator.result.ArraySizeNotEqualResult;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.FieldNotExistResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyComparatorTest {
    private static final String RESOURCE_PATH = "src/test/resources/json/default";
    private BodyComparator bodyComparator;

    @Before
    public void setUp() throws Exception {
        bodyComparator = new DefaultBodyComparator();
    }

    @After
    public void tearDown() throws Exception {
        bodyComparator = null;
    }

    @Test
    public void should_list_non_result_when_json_bodies_are_same() throws IOException {
        JsonNode actual = readFrom("/same/actual.json");
        JsonNode expect = readFrom("/same/expect.json");

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);
        assertThat(compareResults).isEmpty();
    }

    @Test
    public void should_list_result_when_json_bodies_fields_are_not_same() throws IOException {
        JsonNode actual = readFrom("/value-field/actual.json");
        JsonNode expect = readFrom("/value-field/expect.json");

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);
        assertThat(compareResults).isNotEmpty().hasSize(1);
        CompareResult result = compareResults.get(0);

        assertThat(result).isInstanceOf(FieldNotExistResult.class)
                .matches((CompareResult res) -> null == res.getActual() && "four".equals(res.getFieldName()));
    }

    @Test
    public void should_list_result_when_json_bodies_array_list_string_fields_are_not_same() throws IOException {
        JsonNode actual = readFrom("/array-field/size-not-same/actual.json");
        JsonNode expect = readFrom("/array-field/size-not-same/expect.json");


        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);

        assertThat(compareResults).isNotEmpty().hasSize(2);
        Optional<CompareResult> result =
                compareResults.stream().filter(res -> res instanceof ArraySizeNotEqualResult).findAny();

        assertThat(result).isPresent();
        assertThat((ArraySizeNotEqualResult) result.get())
                .hasFieldOrPropertyWithValue("actual", 3)
                .hasFieldOrPropertyWithValue("fieldName", "other")
                .hasFieldOrPropertyWithValue("expect", 5);
    }

    @Test
    public void should_list_result_when_json_bodies_array_list_object_fields_are_not_same() throws IOException {
        JsonNode actual = readFrom("/array-field/value-not-equal/actual.json");
        JsonNode expect = readFrom("/array-field/value-not-equal/expect.json");



        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);

        assertThat(compareResults).isNotEmpty().hasSize(1);
        Optional<CompareResult> result = compareResults.stream().findAny();

        assertThat(result).isPresent();
        assertThat((ValueNotEqualResult) result.get())
                .hasFieldOrPropertyWithValue("actual", "11")
                .hasFieldOrPropertyWithValue("fieldName", "other[1].number")
                .hasFieldOrPropertyWithValue("expect", "10");
    }

    private JsonNode readFrom(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(RESOURCE_PATH + path));
    }
}