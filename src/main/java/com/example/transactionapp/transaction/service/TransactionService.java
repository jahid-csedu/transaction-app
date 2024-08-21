package com.example.transactionapp.transaction.service;

import com.example.transactionapp.transaction.dto.TransactionUpdateDto;
import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import com.example.transactionapp.transaction.specification.TransactionSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Page<Transaction> searchTransactions(String customerId, String accountNumber, String description, Pageable pageable) {
        Specification<Transaction> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and(TransactionSpecification.hasCustomerId(customerId));
        }

        if (accountNumber != null) {
            spec = spec.and(TransactionSpecification.hasAccountNumber(accountNumber));
        }

        if (description != null) {
            spec = spec.and(TransactionSpecification.hasDescriptionLike(description));
        }
        return transactionRepository.findAll(spec, pageable);
    }

    @Transactional
    public void updateTransaction(Long id, TransactionUpdateDto updateDto) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);

        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            transaction.setDescription(updateDto.description());
            transactionRepository.save(transaction);
        } else {
            throw new IllegalArgumentException("Transaction not found");
        }
    }
}
