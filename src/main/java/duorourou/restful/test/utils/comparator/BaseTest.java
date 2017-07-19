package duorourou.restful.test.utils.comparator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duorourou.restful.test.utils.comparator.response.body.BodyComparator;
import duorourou.restful.test.utils.comparator.result.CompareResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class BaseTest {

    private final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private OkHttpClient okHttpClient = builder.build();
    private final ObjectMapper mapper = new ObjectMapper();


    public void get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        JsonNode node = mapper.convertValue(response.body(), JsonNode.class);
        List<CompareResult> results = new BodyComparator().compare(getContract(), node);
    }

    private JsonNode getContract() {
        return null;
    }
}
