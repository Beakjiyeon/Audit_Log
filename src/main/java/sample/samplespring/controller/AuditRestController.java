package sample.samplespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.samplespring.dto.ApiResult;
import sample.samplespring.exception.CustomException;
import sample.samplespring.exception.ErrorCode;
import sample.samplespring.exception.SampleException;
import sample.samplespring.service.AuditService;
import sample.samplespring.util.CommonConstant;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/audit")
public class AuditRestController {

    private final AuditService auditService;

    public AuditRestController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * 감사 타입 정보 조회 메소드
     * @param idx 감사 타입 인덱스
     * @return http 상태 코드, 결과 메세지, 감사타입 정보
     */
    @ExceptionHandler(SampleException.class)
    @GetMapping(value = "type/info/idx/{idx}")
    public ResponseEntity auditTypeInfoByAuditTypeIdx(@PathVariable(value = "idx") String idx) {
        try {
            if(idx == null){
                throw new CustomException(ErrorCode.NULL_IDX_PARAMETER);
            } else {
                return auditService.auditTypeInfo("idx", idx, null).getResponse();
            }
        } catch (RuntimeException e) {
            /* RuntimeException을 사용하는 경우 */
            return new ApiResult(400, e.getMessage(), null).getResponse();
        }

    }


    /**
     * 서비스명 및 타입 조건명을 이용한 감사 타입 조회 메소드
     * @param service 서비스명
     * @param type 타입명
     * @return http 상태 코드, 결과 메세지, 감사타입 정보
     */
    @GetMapping(value = "type/info/service_type/{service}/{type}")
    public ResponseEntity auditTypeInfoByServiceType(@PathVariable(value = "service") String service, @PathVariable(value = "type") String type) {

        if (service == null) {
            throw new CustomException(ErrorCode.NULL_SERVICE_PARAMETER);
        }
        if (type == null) {
            throw new CustomException(ErrorCode.NULL_TYPE_PARAMETER);
        }
        return auditService.auditTypeInfo("service_type", service, type).getResponse();
    }


    /**
     * 감사 타입 정보 목록 전체 조회 메소드
     * @return http 상태 코드, 결과 메세지, 감사타입 정보 목록
     */
    @GetMapping(value = "type/list")
    public ResponseEntity auditTypeInfoList() {
        return auditService.auditTypeInfoList().getResponse();
    }


    /**
     * 서비스명 또는 타입명 이용 감사 타입 정보 목록 전체 조회 메소드
     * @param search 검색 조건 (service/type)
     * @param value 검색 값
     * @return http 상태 코드, 결과 메세지, 감사타입 정보 목록
     */
    @GetMapping(value = "type/list/{search}/{value}")
    public ResponseEntity auditTypeInfoListByServiceOrType (@PathVariable(value = "search") String search, @PathVariable(value = "value") Object value) {

        if (search == null) {
            throw new CustomException(ErrorCode.NULL_SEARCH_TYPE_PARAMETER);
        }
        if (value == null) {
            throw new CustomException(ErrorCode.NULL_SEARCH_VALUE_PARAMETER);
        }
        return auditService.auditTypeInfoList(search, value).getResponse();
    }


    /**
     * 감사 로그 목록 조회 메소드
     * @return http 상태 코드, 결과 메세지, 감사 로그 목록
     */
    @GetMapping(value ="log/list")
    public ResponseEntity auditLogList() {
        return auditService.auditLogList().getResponse();
    }


    /**
     * 서비스명/타입명/이메일/로그일자 이용 감사 로그 목록 조회 메소드
     * @param searchVariables 검색 조건 및 검색 값 정보 객체
     * @return http 상태 코드, 결과 메세지, 감사 로그 목록
     */
    /*
    @GetMapping(value = {
            "log/list/{search1:service|type|email|logdate}/{value1:.+}",
            "log/list/{search1:service|type|email|logdate}/{value1:.+}/{search2:service|type|email|logdate}/{value2:.+}",
            "log/list/{search1:service|type|email|logdate}/{value1:.+}/{search2:service|type|email|logdate}/{value2:.+}/{search3:service|type|email|logdate}/{value3:.+}",
            "log/list/{search1:service|type|email|logdate}/{value1:.+}/{search2:service|type|email|logdate}/{value2:.+}/{search3:service|type|email|logdate}/{value3:.+}/{search4:service|type|email|logdate}/{value4:.+}"
    })

    @GetMapping(value = {
            "log/list/{search1:service|type|email|logdate}/{value1}" +
            "log/list/{search2:service|type|email|logdate}/{value2}" +
            "log/list/{search3:service|type|email|logdate}/{value3}" +
            "log/list/{search4:service|type|email|logdate}/{value4}"
    })
    */
    @GetMapping(value = {
            "log/list/{search1:service|type|email|logdate}/{value1}",
            "log/list/{search1:service|type|email|logdate}/{value1}/{search2:service|type|email|logdate}/{value2}",
            "log/list/{search1:service|type|email|logdate}/{value1}/{search2:service|type|email|logdate}/{value2}/{search3:service|type|email|logdate}/{value3}",
            "log/list/{search1:service|type|email|logdate}/{value1}/{search2:service|type|email|logdate}/{value2}/{search3:service|type|email|logdate}/{value3}/{search4:service|type|email|logdate}/{value4}"
    })
    public ResponseEntity auditLogListByServiceOrType(@PathVariable Map<String, Object> searchVariables) {
        return auditService.auditLogList(searchVariables).getResponse();
    }

}
