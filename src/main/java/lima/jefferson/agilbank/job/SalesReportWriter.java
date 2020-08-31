package lima.jefferson.agilbank.job;

import lima.jefferson.agilbank.entities.Entity;
import lima.jefferson.agilbank.reports.SalesReport;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
public class SalesReportWriter implements ItemWriter<Entity> {

    private final String outputFile;

    @Override
    public void write(List<? extends Entity> entities) throws Exception {
        SalesReport report = new SalesReport((List<Entity>) entities);
        Path outputFilePath = Paths.get(this.outputFile);
        this.createOutputFile(outputFilePath);
        Files.write(outputFilePath, report.toString().getBytes());
    }

    private void createOutputFile(Path outputFile) throws IOException {
        Files.createDirectories(outputFile.getParent());
        if (!Files.exists(outputFile)) {
            Files.createFile(outputFile);
        }
    }
}
