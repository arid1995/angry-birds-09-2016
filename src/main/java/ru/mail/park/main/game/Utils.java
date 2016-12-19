package ru.mail.park.main.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class Utils {
    private ObjectMapper mapper = new ObjectMapper();

    public String buildResponse(String type, ObjectNode data) {

        final ObjectNode response = mapper.createObjectNode();
        response.put("type", type);
        response.set("data", data);
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
