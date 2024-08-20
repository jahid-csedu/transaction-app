package com.example.transactionapp.batch.job.writer;

import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.repository.TransactionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemWriter implements ItemWriter<Transaction> {

    private final TransactionRepository repository;

    public TransactionItemWriter(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends Transaction> items) throws Exception {
        repository.saveAll(items);
    }
}
