package duorourou.restful.testing.utils.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.POJONode;
import duorourou.restful.test.utils.comparator.response.body.BodyComparator;
import duorourou.restful.test.utils.comparator.response.body.DefaultBodyComparator;
import duorourou.restful.test.utils.comparator.result.ArraySizeNotEqualResult;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.comparator.result.FieldNotExistResult;
import duorourou.restful.test.utils.comparator.result.ValueNotEqualResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.api.Assertions.assertThat;

public class BodyComparatorTest {
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
    public void should_list_non_result_when_json_bodies_are_same() {
        Map<String, Object> object = newHashMap();
        object.put("one", 1);
        object.put("two", "2");
        object.put("three", "");

        JsonNode expect = new POJONode(object);
        JsonNode actual = new POJONode(object);

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);
        assertThat(compareResults).isEmpty();
    }

    @Test
    public void should_list_result_when_json_bodies_fields_are_not_same() {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> objectMap = newHashMap();
        objectMap.put("one", 1);
        objectMap.put("two", "2");
        objectMap.put("three", "");

        JsonNode actual = mapper.convertValue(objectMap, JsonNode.class);

        objectMap.put("four", "4");
        JsonNode expect = mapper.convertValue(objectMap, JsonNode.class);

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);
        assertThat(compareResults).isNotEmpty().hasSize(1);
        CompareResult result = compareResults.get(0);

        assertThat(result).isInstanceOf(FieldNotExistResult.class)
                .matches((CompareResult res) -> null == res.getActual() && "four".equals(res.getFieldName()));
    }

    @Test
    public void should_list_result_when_json_bodies_array_list_string_fields_are_not_same() {
        ObjectMapper mapper = new ObjectMapper();
        List<String> values = newArrayList("e", "f", "g");
        Map<String, Object> objectMap = newHashMap();
        objectMap.put("one", 1);
        objectMap.put("two", "2");
        objectMap.put("three", values);

        JsonNode actual = mapper.convertValue(objectMap, JsonNode.class);

        values.add(2, "c");
        objectMap.put("four", "4");
        JsonNode expect = mapper.convertValue(objectMap, JsonNode.class);

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);

        assertThat(compareResults).isNotEmpty().hasSize(2);
        Optional<CompareResult> result =
                compareResults.stream().filter(res -> res instanceof ArraySizeNotEqualResult).findAny();

        assertThat(result).isPresent();
        assertThat((ArraySizeNotEqualResult)result.get())
                .hasFieldOrPropertyWithValue("actual", 3)
                .hasFieldOrPropertyWithValue("fieldName", "three")
                .hasFieldOrPropertyWithValue("expect", 4);
    }

    @Test
    public void should_list_result_when_json_bodies_array_list_object_fields_are_not_same() {
        ObjectMapper mapper = new ObjectMapper();


        List<Foo> values = newArrayList(new Foo("f1", "FOO"), new Foo("f2", "Foo"),
                new Foo("f3", "FOO3"));
        Map<String, Object> objectMap = newHashMap();
        objectMap.put("one", 1);
        objectMap.put("two", "2");
        objectMap.put("three", values);

        JsonNode actual = mapper.convertValue(objectMap, JsonNode.class);
        values.get(1).setName("Foo2");
        JsonNode expect = mapper.convertValue(objectMap, JsonNode.class);

        List<CompareResult> compareResults = bodyComparator.compare(expect, actual);

        assertThat(compareResults).isNotEmpty().hasSize(1);
        Optional<CompareResult> result = compareResults.stream().findAny();

        assertThat(result).isPresent();
        assertThat((ValueNotEqualResult)result.get())
                .hasFieldOrPropertyWithValue("actual", "Foo")
                .hasFieldOrPropertyWithValue("fieldName", "three[1].name")
                .hasFieldOrPropertyWithValue("expect", "Foo2");
    }


}