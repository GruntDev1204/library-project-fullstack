package com.library_management.api.helper.filter_data;

import com.library_management.api.helper.filter_cols.FindBook;
import com.library_management.api.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class BookFilter  {

    public static Specification<Book> getFilter(FindBook requestParam) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            findByName(requestParam.getName(), root, criteriaBuilder, predicates);
            findByAmount(requestParam.getAmount(), root, criteriaBuilder, predicates);
            findByCategory(requestParam.getCategoryId(), root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void findByCategory(Long idCategory, Root<Book> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (idCategory != null) {
            predicates.add(criteriaBuilder.equal(root.get("category").get("id"), idCategory));
        }
    }

    private static void findByAmount(String Amount, Root<Book> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Amount != null) {
            switch (Amount) {
                case "under_20":
                    predicates.add(criteriaBuilder.lessThan(root.get("quantity"), 20));
                    break;
                case "20_to_50":
                    predicates.add(criteriaBuilder.between(root.get("quantity"), 20, 50));
                    break;
                case "50_to_100":
                    predicates.add(criteriaBuilder.between(root.get("quantity"), 50, 100));
                    break;
                case "above_100":
                    predicates.add(criteriaBuilder.greaterThan(root.get("quantity"), 100));
                    break;
                default:
                    break;
            }
        }
    }

    private static void findByName(String name, Root<Book> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
    }
}
