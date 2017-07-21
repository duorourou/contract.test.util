package duorourou.restful.test.utils.assertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duorourou.restful.test.utils.comparator.response.body.DefaultBodyComparator;
import duorourou.restful.test.utils.http.RequestSender;
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
            assertBody(testCase, response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertStatus() {

    }

    private void assertHeaders() {

    }

    private void assertBody(JsonNode testCase, ResponseBody body) throws IOException {
        new DefaultBodyComparator()
                .compare(testCase.get(RESPONSE).get(RESPONSE_BODY), new ObjectMapper().readTree(body.bytes()))
                .forEach(result -> assertThat(result.getFieldName(), result.getActual(), equalTo(result.getExpect())));
    }

}
