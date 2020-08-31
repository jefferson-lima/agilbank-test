package lima.jefferson.agilbank.parsing;

import lima.jefferson.agilbank.records.Record;
import lima.jefferson.agilbank.records.RecordType;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RecordLineMapper implements LineMapper<Record> {

    @Override
    public Record mapLine(String line, int lineNumber) throws Exception {
        String[] lineParts = line.split("ç");

        RecordType recordType = RecordType.getByCode(lineParts[0]);
        List<String> attributes = Arrays.asList(lineParts).subList(1, lineParts.length);

        return new Record(recordType, attributes);
    }
}
