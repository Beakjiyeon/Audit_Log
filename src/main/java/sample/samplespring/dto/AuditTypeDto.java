package sample.samplespring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditTypeDto {

    private long auditTypeIdx;
    private String type;
    private String service;
    private String description;

}
