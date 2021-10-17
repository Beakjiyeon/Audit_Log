package sample.samplespring.model;


import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="dmp_audit_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_log_idx", nullable = false)
    private Long auditLogIdx;

    @Column(name = "audit_type_idx", nullable = false)
    private Long auditTypeIdx;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "user_ip", length = 40)
    private String userIp;

    @Column(name = "event_detail", nullable = false,  length = 65535, columnDefinition="TEXT")
    private String eventDetail;

    @Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    @ManyToOne
    @JoinColumn(name = "audit_type_idx", referencedColumnName = "audit_type_idx", insertable = false, updatable = false)
    private AuditType auditType;
}
