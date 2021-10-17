package sample.samplespring.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class AuditLogDto {

    private long auditLogIdx;
    private long auditTypeIdx;
    private String email;
    private String userIp;
    private String eventDetail;
    private Date eventDate;

    private String type;
    private String service;
    private String description;

}
