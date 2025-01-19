package com.library_management.api.attribute.attribute_data.impl;

import com.library_management.api.attribute.attribute_class.FindTransaction;
import com.library_management.api.attribute.attribute_data.InterfaceAttributeData;
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
public class TransactionAttribute implements InterfaceAttributeData<FindTransaction , Transaction> {
    @Override
    public Specification<Transaction> getAttribute(FindTransaction requestParam) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            this.addStatusPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addIsPurchasedPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addEmailPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addIdBookPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addTransactionDatePredicate(requestParam, root, criteriaBuilder, predicates);
            this.addTransactionQuantityPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addIdCateGoryOfBookPredicate(requestParam, root, criteriaBuilder, predicates);
            this.addNameBookPredicate(requestParam, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void addStatusPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), requestParam.getStatus()));
        }
    }

    private void addIsPurchasedPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIsPurchased() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isPurchased"), requestParam.getIsPurchased()));
        }
    }

    private void addEmailPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getEmail() != null && !requestParam.getEmail().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + requestParam.getEmail() + "%"));
        }
    }

    private void addIdBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIdBook() != null) {
            predicates.add(criteriaBuilder.equal(root.get("idBook"), requestParam.getIdBook()));
        }
    }

    private void addTransactionDatePredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getTransactionDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionDate"), requestParam.getTransactionDate()));
        }
    }

    private void addTransactionQuantityPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getTransactionQuantity() != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionQuantity"), requestParam.getTransactionQuantity()));
        }
    }

    private void addIdCateGoryOfBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getIdCateGoryOfBook() != null) {
            predicates.add(criteriaBuilder.equal(root.get("idCateGoryOfBook"), requestParam.getIdCateGoryOfBook()));
        }
    }

    private void addNameBookPredicate(FindTransaction requestParam, Root<Transaction> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (requestParam.getNameBook() != null && !requestParam.getNameBook().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("nameBook"), "%" + requestParam.getNameBook() + "%"));
        }
    }
}
