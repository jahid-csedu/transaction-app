package com.example.transactionapp.transaction.service;

import com.example.transactionapp.transaction.dto.TransactionUpdateDto;
import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setTrxId(1L);
        transaction.setCustomerId("123");
        transaction.setAccountNumber("456");
        transaction.setDescription("Add Fund");
    }

    @Test
    void searchTransactions_ShouldReturnTransactions_WhenSpecificationsAreProvided() {

        Page<Transaction> transactions = new PageImpl<>(List.of(transaction));
        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(transactions);

        Page<Transaction> result = transactionService.searchTransactions("123", "456", "Add Fund", PageRequest.of(0, 10));

        assertThat(result).isNotEmpty();

        verify(transactionRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void searchTransactions_ShouldReturnEmptyPage_WhenNoTransactionMatches() {

        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(Page.empty());

        Page<Transaction> result = transactionService.searchTransactions("dummy", "dummy", "dummy", PageRequest.of(0, 10));

        assertThat(result).isEmpty();

        verify(transactionRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void updateTransaction_ShouldUpdateTransactionDescription_WhenTransactionExists() {

        TransactionUpdateDto updateDto = new TransactionUpdateDto("Fund Transfer");
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        transactionService.updateTransaction(1L, updateDto);

        assertThat(transaction.getDescription()).isEqualTo("Fund Transfer");

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void updateTransaction_ShouldThrowException_WhenTransactionDoesNotExist() {

        TransactionUpdateDto updateDto = new TransactionUpdateDto("Updated description");
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.updateTransaction(1L, updateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Transaction not found");

        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }
}