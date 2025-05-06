package com.library_management.api.helper.filter_data;

import com.library_management.api.helper.filter_cols.FindTransaction;
import com.library_management.api.model.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class TransactionFilter {

    public static Specification<Transaction> getFilter(FindTransaction requestParam) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            addStatusPredicate(requestParam, root, criteriaBuilder, predicates);
            addIsPurchasedPredicate(requestParam, root, criteriaBuilder, predicates);
            addEmailPredicate(requestParam, root, criteriaBuilder, predicates);
            addIdBookPredicate(requestParam, root, criteriaBuilder, predicates);
            addTransactionDatePredicate(requestParam, root, criteriaBuilder, predicates);
            addTransactionQuantityPredicate(requestParam, root, criteriaBuilder, predicates);
            addIdCateGoryOfBookPredicate(requestParam, root, criteriaBuilder, predicates);
            addNameBookPredicate(requestParam, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addStatusPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), requestParam.getStatus()));
        }
    }

    private static void addIsPurchasedPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIsPurchased() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isPurchased"), requestParam.getIsPurchased()));
        }
    }

    private static void addEmailPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getEmail() != null && !requestParam.getEmail().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + requestParam.getEmail() + "%"));
        }
    }

    private static void addIdBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIdBook() != null) {
            predicates.add(criteriaBuilder.equal(root.get("idBook"), requestParam.getIdBook()));
        }
    }

    private static void addTransactionDatePredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getTransactionDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionDate"), requestParam.getTransactionDate()));
        }
    }

    private static void addTransactionQuantityPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getTransactionQuantity() != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionQuantity"), requestParam.getTransactionQuantity()));
        }
    }

    private static void addIdCateGoryOfBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIdCateGoryOfBook() != null) {
            predicates.add(criteriaBuilder.equal(root.get("idCateGoryOfBook"), requestParam.getIdCateGoryOfBook()));
        }
    }

    private static void addNameBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getNameBook() != null && !requestParam.getNameBook().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("nameBook"), "%" + requestParam.getNameBook() + "%"));
        }
    }
}
