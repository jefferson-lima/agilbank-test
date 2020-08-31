package lima.jefferson.agilbank.records;

import lombok.Data;

import java.util.List;

@Data
public class Record {
    private final RecordType type;
    private final List<String> attributes;
}
