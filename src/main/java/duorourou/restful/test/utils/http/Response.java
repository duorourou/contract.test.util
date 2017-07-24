package duorourou.restful.test.utils.http;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
@Setter
public class Response {
    private Map<String, String> headers;
    private int status;
    private String body;

    private Response(okhttp3.Response response) {
        this.headers = response.headers().names().stream()
                .collect(toMap(name -> name, name -> response.headers().get(name)));
        this.status = response.code();
    }

    public static Response build(okhttp3.Response response) throws IOException {
        Response rp = new Response(response);
        rp.setBody(response.body().string());
        return rp;
    }

    @Override
    public String toString() {
        return "\"headers\" : " + headers.toString()
                + "\n\"status\"" + status
                + "\n\"body\"" + body;
    }
}
