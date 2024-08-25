package com.example.transactionapp.batch.job.writer;

import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionItemWriter implements ItemWriter<Transaction> {

    private final TransactionRepository repository;

    @Override
    public void write(Chunk<? extends Transaction> items) {
        repository.saveAll(items);
    }
}
