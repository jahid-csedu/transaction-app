package com.example.transactionapp.batch.job.reader;

import com.example.transactionapp.transaction.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


class TransactionItemReaderTest {

    private FlatFileItemReader<Transaction> reader;

    @BeforeEach
    void setUp() {
        TransactionItemReader transactionItemReader = new TransactionItemReader();
        reader = transactionItemReader.reader(new ClassPathResource("data/dataSource.txt"));
        reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
    }

    @Test
    void testRead() throws Exception {
        Transaction transaction = reader.read();
        assertThat(transaction).isNotNull();
        assertThat(transaction.getAccountNumber()).isEqualTo("8872838283");
        assertThat(transaction.getTrxAmount()).isEqualByComparingTo("123.00");
        assertThat(transaction.getTrxDate()).isEqualTo(LocalDate.parse("2019-09-12"));
        assertThat(transaction.getTrxTime()).isEqualTo(LocalTime.parse("11:11:11"));
    }
}