package duorourou.restful.test.utils.cases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import duorourou.restful.test.utils.file.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Predicate;

import static com.google.common.collect.Maps.newHashMap;

public class TestCases {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCases.class);

    private static final String CASE_NAME = "case";
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private final Map<String, JsonNode> cases = newHashMap();
    private final Reader reader = new Reader();

    private TestCases() {

    }

    public static TestCases build(String testResourcesPath) {
        TestCases cases = new TestCases();
        try {
            cases.loadAllCases(testResourcesPath);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to load test cases.", e);
        }
        return cases;
    }

    public void loadAllCases(String resourcePath) throws IOException {
        Files.walk(Paths.get(resourcePath)).filter(isJsonFile()).forEach(this::toTestCases);
    }

    private Predicate<Path> isJsonFile() {
        return path -> path.toFile().isFile() && path.toString().endsWith(".json");
    }

    private void toTestCases(Path path) {
        try {
            JsonNode nodes = reader.read(path);
            if (nodes.isArray()) {
                nodes.forEach(this::toTestCase);
            } else {
                toTestCase(nodes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toTestCase(JsonNode node) {
        if (node.isObject() && isValidCase(node)) {
            cases.put(node.get(CASE_NAME).asText(), node);
        }
    }

    private boolean isValidCase(JsonNode node) {
        return node.has(CASE_NAME) && node.get(CASE_NAME).isTextual()
                && node.has(REQUEST)
                && node.has(RESPONSE);
    }

    public JsonNode getCase(String caseName) {
        return cases.getOrDefault(caseName, new TextNode(null));
    }
}
