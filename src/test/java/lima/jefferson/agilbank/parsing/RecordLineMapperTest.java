package lima.jefferson.agilbank.parsing;

import lima.jefferson.agilbank.exceptions.InvalidLineException;
import lima.jefferson.agilbank.records.Record;
import lima.jefferson.agilbank.records.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordLineMapperTest {

    private RecordLineMapper mapper;

    @BeforeEach
    public void beforeEach() {
        this.mapper = new RecordLineMapper();
    }

    @Test
    public void testLineMapper() throws Exception {
        String type = "001";
        String attr1 = "attr1";
        String attr2 = "attr2";
        String attr3 = "attr3";

        String inputString = String.format("%sç%sç%sç%s", type, attr1, attr2, attr3);

        Record record = this.mapper.mapLine(inputString, 0);
        List<String> attributes = record.getAttributes();

        assertEquals(RecordType.SALESMAN, record.getType());
        assertEquals(3, attributes.size());
        assertEquals(attr1, attributes.get(0));
        assertEquals(attr2, attributes.get(1));
        assertEquals(attr3, attributes.get(2));
    }

    @Test
    public void testLineMapperWithEmptyLine() {
        assertThrows(InvalidLineException.class, () -> {
           this.mapper.mapLine("", 0);
        });
    }

}
