package com.example.transactionapp.batch.job;

import com.example.transactionapp.batch.job.listener.JobCompletionNotificationListener;
import com.example.transactionapp.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TransactionLoaderJobConfig {
    private final JobRepository jobRepository;

    @Bean
    public Job transactionJob(Step transactionLoaderStep) {
        var name = "Transaction loader job";
        var builder = new JobBuilder(name, jobRepository);

        return builder.start(transactionLoaderStep)
                .listener(new JobCompletionNotificationListener())
                .build();
    }

    @Bean
    public Step transactionLoaderStep(ItemReader<Transaction> reader,
                      ItemProcessor<Transaction, Transaction> processor,
                      ItemWriter<Transaction> writer,
                      PlatformTransactionManager transactionManager) {
        var name = "Insert transaction data from text file to database step";
        var builder = new StepBuilder(name, jobRepository);

        return builder.<Transaction, Transaction>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
