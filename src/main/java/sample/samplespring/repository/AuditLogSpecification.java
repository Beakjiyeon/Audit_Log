package sample.samplespring.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import sample.samplespring.exception.CustomException;
import sample.samplespring.model.AuditLog;
import sample.samplespring.model.AuditType;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sample.samplespring.exception.ErrorCode.ILLEGAL_DATE_FORMAT;

@Slf4j
public class AuditLogSpecification {

    public static Specification<AuditLog> searchWith(Map<String, Object> searchVariables) {
        return (Specification<AuditLog>) ((root, query, builder) -> {
            Join<AuditLog, AuditType> joinEntity = root.join("auditType", JoinType.LEFT);
            List<Predicate> predicate = getPredicateWithSearchVariables(searchVariables, root, joinEntity, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithSearchVariables(Map<String, Object> searchVariables,
                                                                   Root<AuditLog> root,
                                                                   Join<AuditLog, AuditType> joinEntity,
                                                                   CriteriaBuilder builder) {

        List<Predicate> predicate = new ArrayList<>();

        for (String key : searchVariables.keySet()) {
            switch (key) {
                case "service":
                case "type":
                    predicate.add(builder.equal(
                            joinEntity.get(key),searchVariables.get(key)
                    ));
                    break;

                case "logdate":
                    String entityKey = "eventDate";
                    String logDate = (String) searchVariables.get("logdate");
                    if (!validationDate(logDate)) {
                        throw new CustomException(ILLEGAL_DATE_FORMAT);
                    }

                    /*
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //%Y-%m-%d
                    dateFormat.setLenient(false);

                    predicate.add(builder.like(
                            root.get(entityKey).as(String.class), searchVariables + "%"));
                     */

                    Expression<String> expr = builder.function("DATE_FORMAT", String.class, root.get(entityKey), builder.literal("%Y-%m-%d"));
                    predicate.add(builder.equal(expr, searchVariables.get(key)));
                    break;
            }
        }
        return predicate;
    }

    public static boolean validationDate(String logDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(logDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
