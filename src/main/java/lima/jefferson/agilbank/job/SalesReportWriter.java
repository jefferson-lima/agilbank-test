package lima.jefferson.agilbank.job;

import lima.jefferson.agilbank.entities.Entity;
import lima.jefferson.agilbank.reports.SalesReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class SalesReportWriter implements ItemWriter<Entity> {

    @Value("${output.file.path}")
    private String outputFile;

    @Override
    public void write(List<? extends Entity> entities) throws Exception {
        SalesReport report = new SalesReport((List<Entity>) entities);
        log.info("Sales report:\n" + report.toString());

        Path outputFilePath = this.createOutputFilePath(this.outputFile);
        this.writeReportToFile(report, outputFilePath);
    }

    private Path createOutputFilePath(String outputFile) throws IOException {
        Path outputFilePath = Paths.get(outputFile);

        Files.createDirectories(outputFilePath.getParent());
        if (!Files.exists(outputFilePath)) {
            Files.createFile(outputFilePath);
        }

        return outputFilePath;
    }

    private void writeReportToFile(SalesReport report, Path outputFilePath) throws IOException {
        Files.write(outputFilePath, report.toString().getBytes());
    }
}
