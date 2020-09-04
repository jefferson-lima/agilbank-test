package lima.jefferson.agilbank.parsing;

import lima.jefferson.agilbank.exceptions.InvalidLineException;
import lima.jefferson.agilbank.records.Record;
import lima.jefferson.agilbank.records.RecordType;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RecordLineMapper implements LineMapper<Record> {

    private static final String LINE_PATTERN = "^\\d{3}(ç.*){3}$";
    private static final String FIELD_SEPARATOR = "ç";

    @Override
    public Record mapLine(String line, int lineNumber) throws Exception {

        this.validateLine(line, lineNumber);
        return this.convertLineToRecord(line);
    }

    private void validateLine(String line, int lineNumber) throws InvalidLineException {
        if (!line.matches(LINE_PATTERN)) {
            throw new InvalidLineException(
                String.format("The line %s is invalid and it will be skipped: %s", lineNumber, line)
            );
        }
    }

    private Record convertLineToRecord(String line) {
        String[] lineParts = line.split(FIELD_SEPARATOR);

        RecordType recordType = RecordType.getByCode(lineParts[0]);
        List<String> attributes = Arrays.asList(lineParts).subList(1, lineParts.length);

        return new Record(recordType, attributes);
    }
}
