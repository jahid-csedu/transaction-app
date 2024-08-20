package com.example.transactionapp.batch.job.reader;

import com.example.transactionapp.transaction.entity.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) {
        Transaction transaction = new Transaction();

        transaction.setAccountNumber(fieldSet.readString("accountNumber"));
        transaction.setTrxAmount(fieldSet.readBigDecimal("trxAmount"));
        transaction.setDescription(fieldSet.readString("description"));
        transaction.setTrxDate(LocalDate.parse(fieldSet.readString("trxDate"), DATE_FORMATTER));
        transaction.setTrxTime(LocalTime.parse(fieldSet.readString("trxTime"), TIME_FORMATTER));
        transaction.setCustomerId(fieldSet.readString("customerId"));

        return transaction;
    }
}

