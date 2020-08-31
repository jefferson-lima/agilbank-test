package lima.jefferson.agilbank.records;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum RecordType {
    SALESMAN("001"),
    CLIENT("002"),
    SALE("003");

    private final String code;

    public static RecordType getByCode(String code) {
        return Arrays.stream(RecordType.values())
                .filter(recordType -> recordType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown record type"));
    }
}
