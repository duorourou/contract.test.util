package duorourou.restful.test.utils.assertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duorourou.restful.test.utils.comparator.response.body.DefaultBodyComparator;
import duorourou.restful.test.utils.comparator.response.header.HeaderComparator;
import duorourou.restful.test.utils.comparator.response.status.StatusComparator;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import duorourou.restful.test.utils.http.RequestSender;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Assertion {

    private static final String RESPONSE = "response";
    private static final String RESPONSE_BODY = "body";
    private static final String RESPONSE_HEADER = "headers";
    private static final String RESPONSE_STATUS = "status";

    public void assertion(JsonNode testCase) {
        RequestSender sender = new RequestSender();
        try {
            Response response = sender.send(testCase);
            assertHeaders(testCase.get(RESPONSE).get(RESPONSE_HEADER), response.headers());
            assertStatus(testCase.get(RESPONSE).get(RESPONSE_STATUS), response.code());
            assertBody(testCase.get(RESPONSE).get(RESPONSE_BODY), response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertStatus(JsonNode statusNode, int status) {
        CompareResult result = new StatusComparator().compare(statusNode, status);
        if (result != null) {
            assertThat(result.getFieldName(), result.getActual(), equalTo(result.getExpect()));
        }
    }

    private void assertHeaders(JsonNode headerNode, Headers headers) {
        new HeaderComparator()
                .compare(headerNode, headers)
                .forEach(result -> assertThat(result.getFieldName(), result.getActual(), equalTo(result.getExpect())));
    }

    private void assertBody(JsonNode bodyNode, ResponseBody body) throws IOException {
        new DefaultBodyComparator()
                .compare(bodyNode, new ObjectMapper().readTree(body.bytes()))
                .forEach(result -> assertThat(result.getFieldName(), result.getActual(), equalTo(result.getExpect())));
    }

}
