package duorourou.restful.test.utils.file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Reader {
    private ObjectMapper mapper = new ObjectMapper();

    public JsonNode read(String path) throws IOException {
        return mapper.readTree(new File(path));
    }

    public JsonNode read(Path path) throws IOException {
        return mapper.readTree(path.toFile());
    }

}
