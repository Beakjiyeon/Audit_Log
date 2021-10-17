package sample.samplespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.samplespring.model.AuditType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditTypeRepository extends JpaRepository<AuditType, Long> {
    Optional<AuditType> findByServiceAndType(String service, String type);
    List<AuditType> findAllByService(String service);
    List<AuditType> findAllByType(String type);
}
