package duorourou.restful.test.utils.http;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class RequestSender {

    private static final String URL = "url";
    private static final String METHOD = "method";
    private static final String BODY = "body";

    public Response send(JsonNode node) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return Response.build(client.newCall(getRequest(node.get("request"))).execute());
    }

    public Request getRequest(JsonNode requestNode) {
        return new Request.Builder()
                .url(getUrl(requestNode))
                .method(getMethod(requestNode), requestNode.has(BODY) ? getBody(requestNode.get(BODY)) : null)
                .build();
    }

    private RequestBody getBody(JsonNode body) {
        FormBody.Builder builder = new FormBody.Builder();
        body.fields().forEachRemaining(node -> builder.add(node.getKey(), node.getValue().asText()));
        return builder.build();
    }

    private String getUrl(JsonNode requestNode) {
        return requestNode.get(URL).asText();
    }

    private String getMethod(JsonNode requestNode) {
        return requestNode.get(METHOD).asText();
    }
}
