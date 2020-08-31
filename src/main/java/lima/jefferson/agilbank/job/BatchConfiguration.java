package lima.jefferson.agilbank.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;

@Configuration
@Import({SalesReportJob.class})
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
public class BatchConfiguration {

    private static final String JOB_ID_KEY = "JobID";

    private SimpleJobLauncher jobLauncher;
    private Job salesReportJob;

    @Scheduled(cron = "${repeatInterval}", zone = "America/Recife")
    public void runSalesReportJob() throws Exception {
        JobParameters param = new JobParametersBuilder()
                .addString(JOB_ID_KEY, valueOf(currentTimeMillis()))
                .toJobParameters();

        this.jobLauncher.run(this.salesReportJob, param);
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean(new ResourcelessTransactionManager()).getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        return launcher;
    }
}
