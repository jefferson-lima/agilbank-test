package lima.jefferson.agilbank.records;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordTypeTest {
    @Test
    public void getByCode() {
       assertEquals(RecordType.SALESMAN, RecordType.getByCode("001"));
       assertEquals(RecordType.CLIENT, RecordType.getByCode("002"));
       assertEquals(RecordType.SALE, RecordType.getByCode("003"));
    }

    @Test
    public void getByCodeWithInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> {
           RecordType.getByCode("invalid code");
        });
    }
}
