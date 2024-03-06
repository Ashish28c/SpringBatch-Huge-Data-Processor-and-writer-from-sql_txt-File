package com.loan.radis.Batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.loan.radis.entity.Customer;
import com.loan.radis.repository.CustomerRepository;


@Configuration
public class BatchConfig {
	
	
	@Autowired
	public CustomerRepository customerRepository;
	
	@Autowired
	public PlatformTransactionManager transactionManager;
	

//    @Bean
//    public ItemReader<Customer> reader() {
//        return new SqlDumpFileReader();
//    }
//    
    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/data.txt"));
        itemReader.setName("txtReader");
        itemReader.setLinesToSkip(58);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("BR_NO", "CUSTOMER_NO", "ACCT_NO", "LOAN_BAL", "ST", "NAME", "PAN", "UID_NO", "ADD1",
                "ADD2", "ADD3", "ADD4", "PIN", "TEL_NO", "ACT_TYPE", "ARR_AMT", "NPA_DATE", "OLD_BAD_DEBT_IND", "BAD_DEBT_IND",
                "INCA", "THEO_LOAN_BAL", "THEO_UNPD_PRIN_BAL", "CAP_THEO_UNPD_INT", "THEO_UNPD_ARRS_INT", "UNPD_PRIN_BAL",
                "CAP_UNPD_INT", "UNPD_CHRG_BAL", "THEO_UNPD_CHRG_BAL", "UNPD_ARRS_INT_BAL");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() throws Exception {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("myStep",jobRepository)
                .<Customer, Customer>chunk(10000,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Primary
    @Bean
    public Job myJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) throws Exception {
    	 System.out.println("Starting myJob...");
    	 Job job = new JobBuilder("myJob", jobRepository)
    	            .incrementer(new RunIdIncrementer())
    	            .start(myStep(jobRepository, transactionManager))
    	            .build();

    	    System.out.println("myJob created.");
    	    return job;
    }
    
    @Bean
    public Job runJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("importCustomers",jobRepository)
                .flow(myStep(jobRepository,transactionManager)).end().build();
    }
    
   
    
   
}

