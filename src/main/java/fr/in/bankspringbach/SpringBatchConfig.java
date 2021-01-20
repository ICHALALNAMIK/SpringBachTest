package fr.in.bankspringbach;

import fr.in.bankspringbach.entites.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import  org.springframework.core.io.UrlResource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
@Autowired
private JobBuilderFactory jobBuilderFactory;
    @Autowired
private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired
    private ItemWriter<BankTransaction> bankTransactionItemWriter;
  //  @Autowired private ItemProcessor<BankTransaction, BankTransaction> bankTransactionBankTransactionItemProcessor;


    @Bean
public Job bankJob(){
        Step step1 = stepBuilderFactory.get("step-load-data").<BankTransaction,BankTransaction>chunk(100)
                .reader(bankTransactionItemReader).processor(compositeItemProcessor())
                .writer(bankTransactionItemWriter).build();
        return  jobBuilderFactory.get("bank-data-loader-job").start(step1).build();
    }
@Bean
    public CompositeItemProcessor<BankTransaction,BankTransaction > compositeItemProcessor() {
List< ItemProcessor< BankTransaction,BankTransaction>> itemProcessors= new ArrayList();
itemProcessors.add(bankItemProcessor());
itemProcessors.add(bankItemAnalyticsProcessor());
    CompositeItemProcessor <BankTransaction,BankTransaction > compositeItemProcessor = new CompositeItemProcessor<>();
    compositeItemProcessor.setDelegates(itemProcessors);
    return compositeItemProcessor;
    }

   BankTransactionItemProcessor bankItemProcessor() {
        return new BankTransactionItemProcessor();
    }

    @Bean
     BankTransactionAnalyticsItemProcessor bankItemAnalyticsProcessor() {
        return new BankTransactionAnalyticsItemProcessor();
    }


    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") UrlResource inputFile){
   FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader();
   flatFileItemReader.setName("CSV-READER");
   flatFileItemReader.setLinesToSkip(1);
   flatFileItemReader.setResource( inputFile);
   flatFileItemReader.setLineMapper(LineMapper());
   return flatFileItemReader;
    }
@Bean
    public LineMapper<BankTransaction> LineMapper() {
        DefaultLineMapper< BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","accountID","strTransactionDate","transactionType","amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper< BankTransaction> fieldSetMapper= new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }



}
