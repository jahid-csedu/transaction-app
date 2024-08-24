package com.example.transactionapp.batch.job.config;


import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionLoaderJobConfigTest {
    @Autowired
    private TransactionLoaderJobConfig jobConfig;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void testJobExecution() throws Exception {
        Job job = jobConfig.transactionJob(jobConfig.transactionLoaderStep(null, null, null, null));
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}