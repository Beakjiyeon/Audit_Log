package sample.samplespring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sample.samplespring.dto.ApiResult;
import sample.samplespring.dto.AuditLogDto;
import sample.samplespring.dto.AuditTypeDto;
import sample.samplespring.exception.CustomException;
import sample.samplespring.exception.ErrorCode;
import sample.samplespring.exception.SampleException;
import sample.samplespring.model.AuditLog;
import sample.samplespring.model.AuditType;
import sample.samplespring.repository.AuditLogRepository;
import sample.samplespring.repository.AuditLogSpecification;
import sample.samplespring.repository.AuditTypeRepository;
import sample.samplespring.util.CommonConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Service
public class AuditService {

    private final AuditTypeRepository auditTypeRepository;
    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditTypeRepository auditTypeRepository, AuditLogRepository auditLogRepository) {
        this.auditTypeRepository = auditTypeRepository;
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * 인덱스 또는 서비스명/타입 이용 감사 타입 조회 로직
     * @param searchType 검색 타입 (idx/service_type)
     * @param searchValue1 인덱스값/서비스명
     * @param searchValue2 타입명
     * @return 감사 로그 정보
     */
    @ExceptionHandler(SampleException.class)
    public ApiResult auditTypeInfo(String searchType, Object searchValue1, Object searchValue2) {

        AuditType auditType;

        switch (searchType) {
            case "idx" :
                try {
                    long idx = Long.parseLong(searchValue1.toString());
                    auditType = auditTypeRepository.findById(idx).orElse(null);
                } catch (Exception e) {
                    throw new CustomException(ErrorCode.ILLECAL_IDX_PARAMETER);
                    /* RuntimeException을 사용하는 경우
                    throw new RuntimeException(ErrorCode.ILLECAL_IDX_PARAMETER.getDetail());
                     */
                }
                break;
            case "service_type" :
                String service = searchValue1.toString();
                String type = searchValue2.toString();
                auditType = auditTypeRepository.findByServiceAndType(service, type).orElse(null);
                break;
            default:
                throw new CustomException(ErrorCode.ILLECAL_SEARCH_TYPE_IDX_SERVICE_TYPE);
        }

        if (auditType == null) {
            return new ApiResult(HttpStatus.OK.value(), CommonConstant.NONE_TARGET_ERROR_MSG, null);
        }

        AuditTypeDto auditTypeDto = makeAuditTypeDto(auditType);
        return new ApiResult(HttpStatus.OK.value(), CommonConstant.SUCCESS_MSG, auditTypeDto);
    }


    public ApiResult auditTypeInfoList() {
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        List<AuditTypeDto> auditLogDtoList = auditTypeList.stream().map(auditType -> makeAuditTypeDto(auditType)).collect(Collectors.toList());

        return new ApiResult(HttpStatus.OK.value(), CommonConstant.SUCCESS_MSG, auditLogDtoList);
    }


    public ApiResult auditTypeInfoList(String searchType, Object searchValue) {
        List<AuditType> auditTypeList = null;
        String value = searchValue.toString();

        switch (searchType) {
            case "service" :
                auditTypeList = auditTypeRepository.findAllByService(value);
                break;
            case "type" :
                auditTypeList = auditTypeRepository.findAllByType(value);
                break;
            default:
                throw new CustomException(ErrorCode.ILLECAL_SEARCH_TYPE_IDX_SERVICE_TYPE);
        }

        if (auditTypeList.size() == 0) {
            return new ApiResult(HttpStatus.OK.value(), CommonConstant.NONE_TARGET_ERROR_MSG, auditTypeList);
        }

        List<AuditTypeDto> auditLogDtoList = auditTypeList.stream().map(auditType -> makeAuditTypeDto(auditType)).collect(Collectors.toList());
        return new ApiResult(HttpStatus.OK.value(), CommonConstant.SUCCESS_MSG, auditLogDtoList);
    }


    public ApiResult auditLogList() {

        List<AuditLog> auditLogList = auditLogRepository.findAll();
        List<AuditLogDto> auditLogDtoList = auditLogList.stream().map(auditLog -> makeAuditLogDto(auditLog)).collect(Collectors.toList());

        return new ApiResult(HttpStatus.OK.value(), CommonConstant.SUCCESS_MSG, auditLogDtoList);
    }


    public ApiResult auditLogList(Map<String, Object> searchVariables) {
        List<AuditLog> auditLogList;
        Sort sort = Sort.by(Sort.Direction.DESC, "auditLogIdx");
        log.info("auditLogList function >> " + searchVariables.toString());
        if (searchVariables != null && searchVariables.size() > 0) {
            Map<String, Object> filter = new HashMap<>();

            for (int i = 1; i <= 4; i ++) {
                if (searchVariables.get("search" + i) != null) {
                    filter.put(searchVariables.get("search" + i).toString(), searchVariables.get("value" + i));
                }
            }

            auditLogList = auditLogRepository.findAll(AuditLogSpecification.searchWith(filter), sort);

        } else {
            auditLogList = auditLogRepository.findAll();
        }

        if (auditLogList == null || auditLogList.size() <= 0){
            return new ApiResult(HttpStatus.OK.value(), CommonConstant.NONE_TARGET_ERROR_MSG, auditLogList);
        }
        List<AuditLogDto> auditLogDtoList = auditLogList.stream().map(auditLog -> makeAuditLogDto(auditLog)).collect(Collectors.toList());
        return new ApiResult(HttpStatus.OK.value(), CommonConstant.SUCCESS_MSG, auditLogDtoList);

    }


    public AuditLogDto makeAuditLogDto(AuditLog auditLog) {
        return AuditLogDto.builder()
                .auditLogIdx(auditLog.getAuditLogIdx())
                .auditTypeIdx(auditLog.getAuditTypeIdx())
                .email(auditLog.getEmail())
                .userIp(auditLog.getUserIp())
                .eventDetail(auditLog.getEventDetail())
                .eventDate(auditLog.getEventDate())
                .type(auditLog.getAuditType().getType())
                .service(auditLog.getAuditType().getService())
                .description(auditLog.getAuditType().getDescription())
                .build();
    }


    public AuditTypeDto makeAuditTypeDto(AuditType auditType) {
        return AuditTypeDto.builder()
                .auditTypeIdx(auditType.getAuditTypeIdx())
                .service(auditType.getService())
                .type(auditType.getType())
                .description(auditType.getDescription())
                .build();
    }


}
