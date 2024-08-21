package com.example.transactionapp.transaction.specification;

import com.example.transactionapp.transaction.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    private TransactionSpecification() {

    }
    public static Specification<Transaction> hasCustomerId(String customerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("customerId"), customerId);
    }

    public static Specification<Transaction> hasAccountNumber(String accountNumber) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accountNumber"), accountNumber);
    }

    public static Specification<Transaction> hasDescriptionLike(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }
}
