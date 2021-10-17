package sample.samplespring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */

    /* NULL */
    NULL_IDX_PARAMETER(BAD_REQUEST, "감사타입 인덱스 정보가 누락되었습니다."),
    NULL_SERVICE_PARAMETER(BAD_REQUEST, "서비스 정보가 누락되었습니다."),
    NULL_TYPE_PARAMETER(BAD_REQUEST, "타입 정보가 누락되었습니다."),
    NULL_SEARCH_TYPE_PARAMETER(BAD_REQUEST, "검색 조건 정보가 누락되었습니다."),
    NULL_SEARCH_VALUE_PARAMETER(BAD_REQUEST, "검색 값 정보가 누락되었습니다."),

    /* ILLEGAL */
    ILLECAL_IDX_PARAMETER(BAD_REQUEST, "검색 조건 idx 정보는 숫자만 가능합니다."),
    ILLECAL_SEARCH_TYPE_IDX_SERVICE_TYPE(BAD_REQUEST, "검색 조건은 idx 또는 service_type 만 가능합니다."),
    ILLEGAL_DATE_FORMAT(BAD_REQUEST, "로그일자 날짜 포맷은 ('yyyy-MM-dd') 입니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 리소스 정보를 찾을 수 없습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}