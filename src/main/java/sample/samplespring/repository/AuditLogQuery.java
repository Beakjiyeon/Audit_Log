package sample.samplespring.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sample.samplespring.dto.AuditLogDto;
import sample.samplespring.model.AuditLog;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * db connection, query test
 * 추후 삭제 예정
 */
@Slf4j
@Repository
public class AuditLogQuery {
    /*

    @PersistenceContext
    EntityManager em;

    public List<AuditLogDto> getAuditLogs(Map<String, Integer> pageInfo) {

        // native query test
        StringBuffer whereCondition = new StringBuffer();
        String selectQuery = "SELECT email, user_ip, event_detail, event_date" +
                "             FROM dmp_audit_log";

        Query query = em.createNativeQuery(selectQuery);

        List<Object[]> list = query.getResultList();
        List<AuditLogDto> x =
                list.stream().map(arr -> new AuditLogDto((String)arr[0],
                                (String)arr[1],
                                (String)arr[2],
                                (Date)arr[3]))
                        .collect(Collectors.toList());


        // criteria query test
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<AuditLogDto> qry = builder.createQuery(AuditLogDto.class);
        Root<AuditLog> root = qry.from(AuditLog.class);

        Map<String, Join> joinInfo = new HashMap<>(); //getJoinInfo(root);
        setSelect(joinInfo, builder, qry, root);
        query.setFirstResult(0).setMaxResults(1);

        List<AuditLogDto> dtos = query.getResultList();
        log.info("db test2" + dtos);
        return dtos;
        return null;
    }

    private void setSelect(Map<String, Join> joinInfo, CriteriaBuilder builder, CriteriaQuery qry, Root root) {
        qry.select(builder.construct(AuditLogDto.class,
                root.get("email"), root.get("userIp"), root.get("eventDetail"), root.get("eventDate")
        ));
    }

 */
}
