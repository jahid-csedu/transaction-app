package com.example.transactionapp.transaction.controller;

import com.example.transactionapp.transaction.dto.TransactionUpdateDto;
import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<Transaction>> getTransactions(@PageableDefault Pageable pageRequest,
                                                                  @RequestParam(value = "customer-id", required = false) String customerId,
                                                                  @RequestParam(value = "account-number", required = false) String accountNumber,
                                                                  @RequestParam(value = "description", required = false) String description) {
        Page<Transaction> transactions = transactionService.searchTransactions(customerId, accountNumber, description, pageRequest);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransactionDescription(
            @PathVariable Long id,
            @RequestBody TransactionUpdateDto updateDto) {
        log.info("Updating transaction {} with data {}", id, updateDto);
        transactionService.updateTransaction(id, updateDto);
        return ResponseEntity.ok("Updated");
    }
}
