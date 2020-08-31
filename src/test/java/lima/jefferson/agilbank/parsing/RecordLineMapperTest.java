package lima.jefferson.agilbank.parsing;

import lima.jefferson.agilbank.records.Record;
import lima.jefferson.agilbank.records.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        String inputString = String.format("%sç%sç%s", type, attr1, attr2);

        Record record = this.mapper.mapLine(inputString, 0);
        List<String> attributes = record.getAttributes();

        assertEquals(RecordType.SALESMAN, record.getType());
        assertEquals(2, attributes.size());
        assertEquals(attr1, attributes.get(0));
        assertEquals(attr2, attributes.get(1));
    }

}
