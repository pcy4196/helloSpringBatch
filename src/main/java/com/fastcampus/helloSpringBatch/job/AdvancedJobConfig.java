package com.fastcampus.helloSpringBatch.job;

import com.fastcampus.helloSpringBatch.job.validator.LocalDateParameterValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@AllArgsConstructor
@Slf4j
public class AdvancedJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job advancedJob(JobExecutionListener jobExecutionListener,
                           Step advancedStep) {
        return jobBuilderFactory.get("advancedJob")
                .incrementer(new RunIdIncrementer())
                .validator(new LocalDateParameterValidator("targetDate"))
                .listener(jobExecutionListener)
                .start(advancedStep)
                .build();
    }

    @JobScope
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("[jobExecutionListener#beforeJob] jobExecution is " + jobExecution.getStatus());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.FAILED) {
                    // E-MAIL ?????? ??????????????? ?????? ??????(?????? ???????????? ?????? ??????)
                    log.info("[jobExecutionListener#afterJob] jobExecution is FAILED!!!! RECOVER ASAP");
                } else {
                    log.info("[jobExecutionListener#afterJob] jobExecution is " + jobExecution.getStatus());
                }
            }
        };
    }

    @JobScope
    @Bean
    public Step advancedStep(Tasklet advancedTasklet,
                             StepExecutionListener stepExecutionListener) {
        return stepBuilderFactory.get("advancedStep")
                .listener(stepExecutionListener)
                .tasklet(advancedTasklet)
                .build();
    }

    @StepScope
    @Bean
    public StepExecutionListener stepExecutionListener() {
        // stepExecutionListener ?????? ??????????????? ?????? Listener
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("[stepExecutionListener#beforeStep] stepExecution is " + stepExecution.getStatus());
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                log.info("[stepExecutionListener#afterStep] stepExecution is " + stepExecution.getStatus());
                return stepExecution.getExitStatus();
            }
        };
    }


    @StepScope
    @Bean
    public Tasklet advancedTasklet(@Value("#{jobParameters['targetDate']}") String targetDate) {
        return (contribution, chunkContext) -> {
            log.info("[advancedTasklet] excuted advancedTasklet");
            log.info("[advancedTasklet] JobParameter - targetDate = " + targetDate);
            LocalDate execute = LocalDate.parse(targetDate);
//            // ERROR ?????? - 2022-06-15
//            throw new RuntimeException("OCCER ERROR!!!");
            return RepeatStatus.FINISHED;
        };
    }
}
