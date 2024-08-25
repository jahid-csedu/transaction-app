package com.example.transactionapp.batch.job.writer;

import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransactionItemWriterTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionItemWriter transactionItemWriter;

    @Test
    void testWrite() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber("123456");
        transaction.setTrxAmount(new BigDecimal("100.00"));

        when(transactionRepository.saveAll(any())).thenReturn(List.of(transaction));

        transactionItemWriter.write(new Chunk<>(List.of(transaction)));

        verify(transactionRepository, times(1)).saveAll(any());
    }
}