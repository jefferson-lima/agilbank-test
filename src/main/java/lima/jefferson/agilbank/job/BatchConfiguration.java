package lima.jefferson.agilbank.job;

import lima.jefferson.agilbank.entities.Entity;
import lima.jefferson.agilbank.parsing.RecordLineMapper;
import lima.jefferson.agilbank.parsing.RecordProcessor;
import lima.jefferson.agilbank.records.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
public class BatchConfiguration {

    public static final String SALES_REPORT_JOB_NAME = "saleReportJob";
    private static final String GENERATE_REPORT_STEP_NAME = "generateReport";
    private static final int CHUNK_SIZE = 10000;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Value("${output.file.path}")
    private String outputFilePath;

    @Value("file:${input.directory}/*.${input.extension}")
    private Resource[] inputResources;

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        return launcher;
    }

    @Bean
    public Job getJob(Step generateReportStep) {
        return this.jobBuilderFactory.get(SALES_REPORT_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(generateReportStep)
                .build();
    }

    @Bean
    public Step generateReportStep(MultiResourceItemReader<Record> multiResourceReader, RecordProcessor processor) {
        return this.stepBuilderFactory.get(GENERATE_REPORT_STEP_NAME)
                .<Record, Entity> chunk(CHUNK_SIZE)
                .reader(multiResourceReader)
                .processor(processor)
                .writer(new SalesReportWriter(this.outputFilePath))
                .build();
    }

    @Bean
    public MultiResourceItemReader<Record> multiResourceReader(FlatFileItemReader<Record> singleFileReader) {
        return new MultiResourceItemReaderBuilder<Record>()
                .name("multiFileReader")
                .delegate(singleFileReader)
                .resources(this.inputResources)
                .build();
    }

    @Bean
    public FlatFileItemReader<Record> singleFileReader(RecordLineMapper lineMapper) {
        return new FlatFileItemReaderBuilder<Record>()
                .name("stringFileReader")
                .lineMapper(lineMapper)
                .build();
    }
}
