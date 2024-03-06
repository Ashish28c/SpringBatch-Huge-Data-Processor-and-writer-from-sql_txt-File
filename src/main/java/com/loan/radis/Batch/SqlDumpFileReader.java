package com.loan.radis.Batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.loan.radis.entity.Customer;

public class SqlDumpFileReader implements ItemReader<Customer> {

    private FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();

    public SqlDumpFileReader() {
        reader.setResource(new ClassPathResource("data.txt"));
        reader.setLinesToSkip(56);
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames("brNo", "customerNo", "acctNo", "loanBal", "stat", "name", "pan", "uidNo", "add1",
                "add2", "add3", "add4", "pin", "telNo", "actType", "arrAmt", "npaDate", "oldBadDebtInd", "badDebtInd",
                "inca", "theoLoanBal", "theoUnpdPrinBal", "capTheoUnpdInt", "theoUnpdArrsInt", "unpdPrinBal",
                "capUnpdInt", "unpdChrgBal", "theoUnpdChrgBal", "unpdArrsIntBal");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return reader.read();
    }
}
