package sample.samplespring.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String message;
    private final Map<Object, Object> data = null;
    //private final String error;
    //private final String code;
    //private final Map data;


    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        //.error(errorCode.getHttpStatus().name())
                        //.code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }

    /*
    public static ResponseEntity<ErrorResponse> toResponseEntity(String requestURL) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        //.error(errorCode.getHttpStatus().name())
                        //.code(errorCode.name())
                        .message("pathVariable 정보가 누락되었습니다.")
                        .build()
                );

    }
     */
}