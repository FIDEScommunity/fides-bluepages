package community.fides.bluepages.backend.repository;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class CriteriaApiUtils {

    public static Join<?, ?> join(From<?, ?> from, String property, JoinType joinType) {
        return from.getJoins().stream()
                .filter(j -> j.getAttribute().getName().equals(property) && j.getJoinType().equals(joinType))
                .findFirst()
                .orElseGet(() -> from.getFetches().stream()
                        .filter(j -> j.getAttribute().getName().equals(property) && j.getJoinType().equals(joinType))
                        .map(f -> (Join) f)
                        .findFirst()
                        .orElseGet(() -> from.join(property, joinType)));
    }
//    public static <X, Y> ListJoin<X, Y> join(Root<X> criteriaRoot,
//                                             ListAttribute<? super X, Y> attribute,
//                                             JoinType joinType
//    ) {
//        return (ListJoin<X, Y>) criteriaRoot.getJoins().stream()
//                .filter(j -> j.getAttribute().getName().equals(attribute.getName()) && j.getJoinType().equals(joinType))
//                .findFirst()
//                .orElseGet(() -> criteriaRoot.join(attribute, joinType));
//    }
}
