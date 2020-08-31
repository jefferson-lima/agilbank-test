package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Salesman;
import lima.jefferson.agilbank.records.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalesmanConverterTest {

    private Converter<Salesman> converter;

    @BeforeEach
    public void beforeEach() {
        this.converter = new SalesmanConverter();
    }

    @Test
    public void convertFromString() {
        String cpf = "1234567891234";
        String name = "Pedro";
        String salary = "50000";

        List<String> attributes = Arrays.asList(cpf, name, salary);
        Salesman salesman = this.converter.fromStringList(attributes);

        assertEquals(cpf, salesman.getCpf());
        assertEquals(name, salesman.getName());
        assertEquals(new BigDecimal(salary), salesman.getSalary());
    }

    @Test
    public void saveWithWrongNumberOfAttributes() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> attributes = Arrays.asList("12313");
            this.converter.fromStringList(attributes);
        });
    }

    @Test
    public void saveWithNonNumericSalary() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> attributes = Arrays.asList(RecordType.SALE.getCode(), "1234567891234", "Pedro", "salary");
            this.converter.fromStringList(attributes);
        });
    }

}
