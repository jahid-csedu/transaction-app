package com.example.transactionapp.batch.job.writer;

import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionItemWriterTest {

    @Autowired
    private TransactionItemWriter transactionItemWriter;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testWrite() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber("123456");
        transaction.setTrxAmount(new BigDecimal("100.00"));

        transactionItemWriter.write(new Chunk<>(List.of(transaction)));

        assertThat(transactionRepository.findAll()).isNotNull();
    }
}