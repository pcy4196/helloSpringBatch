
### HelloSpringBatchApplication
***
+ practice spring-batch in springBoot
  1. Ch01. 프로젝트 환경설정 
     1. helloJob batch 잡 작성 및 수행
  2. Spring Batch 기본 프로젝트
     + 데이터 (Read,Proc,Writer) 처리
        + PlainTextJob 구현
  3. Spring Batch TEST
      + BatchTestConfig 구현
      + HelloJobConfigTest 구현
      + PlainTextJobConfigTest 구현
  4. JobParameterValidator
      + AdvancedJobConfig 구현
      + LocalDateParameterValidator 구현
  5. JobExecutionListener
      + AdvancedJobConfig의 jobExecutionListener Method 구현
  6. FlatFileItemReader
      + FlatFileJobConfig 구현(PlayerFieldSetMapper, PlayerDto)
  7. StepListener
      + AdvancedJobConfig의 stepExecutionListener Method 구현
  8. ItemProcessorAdapter
      + FlatFileJobConfig의 playerSalaryItemProcessorAdapter Method 구현  8. ItemProcessorAdapter
  9. FlatFileItemWriter
      + FlatFileJobConfig의 playerFileItemWriter Method 구현