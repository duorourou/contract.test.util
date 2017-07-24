package duorourou.restful.test.utils.assertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duorourou.restful.test.utils.comparator.response.body.DefaultBodyComparator;
import duorourou.restful.test.utils.comparator.response.header.HeaderComparator;
import duorourou.restful.test.utils.comparator.response.status.StatusComparator;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.http.RequestSender;
import duorourou.restful.test.utils.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class Assertion {
    private static final Logger LOGGER = LoggerFactory.getLogger(Assertion.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String RESPONSE = "response";
    private static final String RESPONSE_BODY = "body";
    private static final String RESPONSE_HEADER = "headers";
    private static final String RESPONSE_STATUS = "status";

    public void assertion(JsonNode testCase) {
        RequestSender sender = new RequestSender();
        try {
            Response response = sender.send(testCase);
            List<CompareResult> results = compare(response, testCase);
            doAssert(response, testCase, results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<CompareResult> compare(Response response, JsonNode expect) throws IOException {
        List<CompareResult> results = newArrayList();
        results.addAll(compareHeaders(expect.get(RESPONSE).get(RESPONSE_HEADER), response.getHeaders()));
        results.add(compareStatus(expect.get(RESPONSE).get(RESPONSE_STATUS).asInt(), response.getStatus()));
        results.addAll(compareBody(expect.get(RESPONSE).get(RESPONSE_BODY), response.getBody()));
        return results.stream().filter(CompareResult::isNotNone).collect(toList());
    }

    private void doAssert(Response response, JsonNode expect, List<CompareResult> results) {
        if (!results.isEmpty()) {
            LOGGER.info("Expect : {} ", expect.get(RESPONSE).toString().replaceAll(LINE_SEPARATOR, " "));
            LOGGER.error("Actual : {} ", response.toString());
            throw new AssertionError(results.stream()
                    .map(CompareResult::toString).reduce((a, b) -> a + b).get());
        }

    }

    private CompareResult compareStatus(int expectStatus, int status) {
        return new StatusComparator().compare(expectStatus, status);
    }

    private List<CompareResult> compareHeaders(JsonNode headerNode, Map<String, String> headers) {
        return new HeaderComparator().compare(headerNode, headers);
    }

    private List<CompareResult> compareBody(JsonNode bodyNode, String body) throws IOException {
        return new DefaultBodyComparator().compare(bodyNode, new ObjectMapper().readTree(body.getBytes()));
    }

    private String buildErrorMessage(List<CompareResult> compareResults) {
        StringBuilder builder = new StringBuilder();
        compareResults.forEach(result -> builder.append(""));

        return builder.toString();
    }
}
