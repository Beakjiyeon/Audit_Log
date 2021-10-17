package sample.samplespring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data //@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredConstructor
@Builder
@AllArgsConstructor
public class ApiResult {

    private int status;
    private String message;
    private Object data;


    public ResponseEntity getResponse() {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        body.put("data", data);
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

}
