package lima.jefferson.agilbank.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private static final String JOB_ID_KEY = "JobID";

    private final JobLauncher jobLauncher;
    private final Job salesReportJob;

    @Scheduled(cron = "${repeatInterval}", zone = "America/Recife")
    public void runSalesReportJob() throws Exception {
        JobParameters param = new JobParametersBuilder()
                .addString(JOB_ID_KEY, valueOf(currentTimeMillis()))
                .toJobParameters();

        this.jobLauncher.run(this.salesReportJob, param);
    }
}
