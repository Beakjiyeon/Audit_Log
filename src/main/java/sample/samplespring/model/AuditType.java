package sample.samplespring.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="dmp_audit_type")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AuditType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_type_idx", nullable = false)
    private Long auditTypeIdx;

    @Column(length = 20, nullable = false)
    private String type;

    @Column(length = 20, nullable = false)
    private String service;

    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "auditType", fetch = FetchType.LAZY)
    private List<AuditLog> auditLog;
}
