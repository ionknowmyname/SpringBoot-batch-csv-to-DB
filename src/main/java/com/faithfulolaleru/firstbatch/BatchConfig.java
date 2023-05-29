package com.faithfulolaleru.firstbatch;


import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@AllArgsConstructor
public class BatchConfig {

    // private JobBuilderFactory jobBuilderFactory;

    // private StepBuilderFactory stepBuilderFactory;

    private JobRepository jobRepository;

    private PlatformTransactionManager transactionManager;

    private DataSource dataSource;

    private final CsvReader csvReader;

    private final CustomWriter customWriter;

//    private AccountRepository accountRepository;


    @Bean
    public FlatFileItemReader<Account> reader() {
        FlatFileItemReader<Account> itemReader = new FlatFileItemReader<>();

        // itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        // itemReader.setResource(new FileSystemResource("D:/mydata/invoices.csv"));  // file not in project bt on HDD
        // itemReader.setResource(new UrlResource("https://xyz.com/files/invoices.csv"));  // file is remote

        itemReader.setResource(new ClassPathResource("/Book1.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        // for when there's blank lines in between the record
        itemReader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());

        return itemReader;
    }

    /*@Bean
    public ItemReader<Account> csvItemReader() throws Exception {
        // Path filePath = Paths.get("Book1.csv");

        File file = ResourceUtils.getFile("classpath:Book1.csv");
        String path = file.getPath();
        List<Account> data = csvReader.readCsvFile2(Path.of(path));

        return new IteratorItemReader<>(data);
    }*/

    private LineMapper<Account> lineMapper() {
        DefaultLineMapper<Account> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);   // optional
        // lineTokenizer.setNames("id", "code", "account_name", "type", "allow_reconciliation", "external_id");

        lineTokenizer.setNames(new String[] { "id", "code", "account_name", "type", "allow_reconciliation", "external_id" });
        // lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4, 5 });

        BeanWrapperFieldSetMapper<Account> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Account.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public ItemProcessor<Account, Account> processor() {  // can also explicitly return AccountProcessor type
        return new AccountProcessor();
    }

    // for JPA impl
//    @Bean
//    public RepositoryItemWriter<Account> writer() {
//        RepositoryItemWriter<Account> writer = new RepositoryItemWriter<>();
//        writer.setRepository(accountRepository);
//        writer.setMethodName("save");
//
//        return writer;
//    }

//    @Bean
//    public ItemWriter<Account> writer2(){
//        // return new InvoiceItemWriter(); // Using lambda expression code instead of a separate implementation
//
//        return accounts -> {
//            System.out.println("Saving Account Records --> " + accounts);
//            accountRepository.saveAll(accounts);
//        };
//    }


    // for JDBC  impl
    @Bean
    public JdbcBatchItemWriter<Account> writer3() {
        JdbcBatchItemWriter<Account> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO account (id, code, account_name, type, allow_reconciliation, external_id) " +
                "VALUES (:id, :code, :accountName, :type, :allowReconciliation, :externalId)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        return itemWriter;
    }


    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);

        return asyncTaskExecutor;
    }

/*    @Bean
    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1").<Account, Account>chunk(3)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .taskExecutor(taskExecutor())
//                .build();

        return new StepBuilder("step1", jobRepository).<Account, Account>chunk(3, transactionManager)
                .reader(csvItemReader())
//                .processor(processor())
                .writer(customWriter)
//                .taskExecutor(taskExecutor())
                .build();
    }*/

    /*@Bean
    public Job runJob() throws Exception {
//        return jobBuilderFactory.get("runJob")
//                .flow(step1()).end().build();

        return new JobBuilder("runJob", jobRepository)
                .start(step1()).build();

    }

     */
}
