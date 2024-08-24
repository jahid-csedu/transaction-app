package com.example.transactionapp.batch.job.processor;

import com.example.transactionapp.transaction.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionItemProcessorTest {
    @Autowired
    private TransactionItemProcessor transactionItemProcessor;

    @Test
    void testProcess() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber("123456");
        transaction.setTrxAmount(new BigDecimal("100.00"));

        Transaction processedTransaction = transactionItemProcessor.process(transaction);
        assertThat(processedTransaction).isNotNull();
        assertThat(processedTransaction.getAccountNumber()).isEqualTo("123456");
        assertThat(processedTransaction.getTrxAmount()).isEqualByComparingTo("100.00");
    }
}