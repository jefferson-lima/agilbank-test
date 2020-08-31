package lima.jefferson.agilbank;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
public class SalesReportJobTest {

    @Value("${output.file.path}")
    private String outputFilePath;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    public void deleteOutputFile() {
        File outputFile = this.getOutputFile();

        if(outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void testJobExecuteSuccessfully() throws Exception {
        JobExecution execution = this.jobLauncherTestUtils.launchJob();
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
    }

    @Test
    public void testOutputFileWasCreated() throws Exception {
        this.jobLauncherTestUtils.launchJob();
        File outputFile = this.getOutputFile();
        assertTrue(outputFile.canRead());
    }

    @Test
    public void testOutputFileContent() throws Exception {
        this.jobLauncherTestUtils.launchJob();

        List<String> fileLines = Files.readAllLines(this.getOutputFile().toPath());
        assertEquals(4, fileLines.size());

        List<String> values = fileLines.stream()
                .map(line -> line.split(":")[1].trim())
                .collect(Collectors.toList());

        assertEquals("3", values.get(0), "Wrong number of clients");
        assertEquals("3", values.get(1), "Wrong number of salesmen");
        assertEquals("20", values.get(2), "Wrong most expensive sale id");
    }

    private File getOutputFile() {
        return Paths.get(this.outputFilePath).toFile();
    }

}
