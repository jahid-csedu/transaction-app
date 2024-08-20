package com.example.transactionapp.batch.job.reader;

import com.example.transactionapp.transaction.entity.Transaction;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionItemReader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Bean
    public FlatFileItemReader<Transaction> reader(@Value("classpath:data/dataSource.txt") Resource resource) {
        return new FlatFileItemReaderBuilder<Transaction>()
                .name("Transaction Item Reader")
                .resource(resource)
                .linesToSkip(1)
                .lineTokenizer(getLineTokenizer())
                .fieldSetMapper(getTransactionFieldSetMapper())
                .build();
    }

    private LineTokenizer getLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer("|");
        tokenizer.setNames("accountNumber", "trxAmount", "description", "trxDate", "trxTime", "customerId");
        return tokenizer;
    }

    private FieldSetMapper<Transaction> getTransactionFieldSetMapper() {
        return fieldSet -> {
            Transaction transaction = new Transaction();
            transaction.setAccountNumber(fieldSet.readString("accountNumber"));
            transaction.setTrxAmount(fieldSet.readBigDecimal("trxAmount"));
            transaction.setDescription(fieldSet.readString("description"));
            transaction.setTrxDate(LocalDate.parse(fieldSet.readString("trxDate"), DATE_FORMATTER));
            transaction.setTrxTime(LocalTime.parse(fieldSet.readString("trxTime"), TIME_FORMATTER));
            transaction.setCustomerId(fieldSet.readString("customerId"));
            return transaction;
        };
    }
}
