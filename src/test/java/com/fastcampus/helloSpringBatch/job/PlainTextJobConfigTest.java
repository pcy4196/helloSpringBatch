package com.fastcampus.helloSpringBatch.job;

import com.fastcampus.helloSpringBatch.BatchTestConfig;
import com.fastcampus.helloSpringBatch.core.domain.PlainText;
import com.fastcampus.helloSpringBatch.core.repository.PlainTextRepository;
import com.fastcampus.helloSpringBatch.core.repository.ResultTextRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.IntStream;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") // application.yml의 test 설정 사용
@ContextConfiguration(classes = {PlainTextJobConfig.class, BatchTestConfig.class})
class PlainTextJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlainTextRepository plainTextRepository;

    @Autowired
    private ResultTextRepository resultTextRepository;

    @AfterEach
    public void tearDown() {
        // h2 메모리 데이터가 정리 될 수 있도록 TEST 이후 해당 테이블 데이터 삭제 처리
        plainTextRepository.deleteAll();
        resultTextRepository.deleteAll();
    }

    @Test
    public void success_givenNoPlainText() throws Exception {
        // Given
        // no plainText

        // When
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // Then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(0, resultTextRepository.count());
    }

    @Test
    public void success_givenPlainText() throws Exception {
        // Given
        IntStream.range(0, 12)
                .forEach(
                        num -> plainTextRepository.save(new PlainText("text" + num))
                );

        // When
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // Then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(12, resultTextRepository.count());
    }
}