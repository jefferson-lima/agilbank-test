package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Client;
import lima.jefferson.agilbank.records.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientConverterTest {

    private Converter<Client> converter;

    @BeforeEach
    public void beforeEach() {
        this.converter = new ClientConverter();
    }

    @Test
    public void convertFromString() {
        String cnpj = "2345675434544345";
        String name = "Jose da Silva";
        String businessArea = "Rural";

        List<String> attributes = Arrays.asList(cnpj, name, businessArea);
        Client client = this.converter.fromStringList(attributes);

        assertEquals(cnpj, client.getCnpj());
        assertEquals(name, client.getName());
        assertEquals(businessArea, client.getBusinessArea());
    }

    @Test
    public void saveWithWrongNumberOfAttributes() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> attributes = Arrays.asList("1233");
            this.converter.fromStringList(attributes);
        });
    }
}
