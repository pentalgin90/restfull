package ca.home.novacom.restfull.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductSpec implements Specification {

    private List<FilterProduct> filters;

    public ProductSpec() {
        filters = new ArrayList<>();
    }

    public void addFilter(FilterProduct filter) {
        filters.add(filter);
    }


    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);

        return predicates.size() > 1
                ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach(filter -> predicates.add(buildPredicate(filter, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    private Predicate buildPredicate(FilterProduct filter, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return buildEqualsPredicateToCriteria(filter, root, criteriaQuery, criteriaBuilder);
    }

    private Predicate buildEqualsPredicateToCriteria(FilterProduct filter, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(filter.getField()), filter.getValue());
    }


}
