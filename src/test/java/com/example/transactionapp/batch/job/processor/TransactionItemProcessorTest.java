package com.example.transactionapp.batch.job.processor;

import com.example.transactionapp.transaction.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionItemProcessorTest {
    private TransactionItemProcessor transactionItemProcessor;

    @BeforeEach
    void setup() {
        transactionItemProcessor = new TransactionItemProcessor();
    }

    @Test
    void testProcess() {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber("123456");
        transaction.setTrxAmount(new BigDecimal("100.00"));

        Transaction processedTransaction = transactionItemProcessor.process(transaction);
        assertThat(processedTransaction).isNotNull();
        assertThat(processedTransaction.getAccountNumber()).isEqualTo("123456");
        assertThat(processedTransaction.getTrxAmount()).isEqualByComparingTo("100.00");
    }
}