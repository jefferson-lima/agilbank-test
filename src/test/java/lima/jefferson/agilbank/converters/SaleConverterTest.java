package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Sale;
import lima.jefferson.agilbank.entities.SaleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SaleConverterTest {

    private Converter<Sale> converter;

    @BeforeEach
    public void beforeEach() {
        this.converter = new SaleConverter();
    }

    @Test
    public void convertFromString() {
        String id = "10";
        String items = "[1-34-10,2-33-1.50,3-40-0.10]";
        String salesmanName = "Paulo";

        List<String> attributes = Arrays.asList(id, items, salesmanName);
        Sale sale = this.converter.fromStringList(attributes);

        assertEquals(Long.parseLong(id), sale.getId());
        assertEquals(salesmanName, sale.getSalesmanName());
    }

    @Test
    public void convertFromStringSalesItem() {
        List<String> attributes = Arrays.asList("10", "[1-34-10,2-33-1.50,3-40-0.10]", "Paulo");
        Sale sale = this.converter.fromStringList(attributes);

        List<SaleItem> saleItems = sale.getSaleItems();

        SaleItem firstItem = saleItems.get(0);
        SaleItem secondItem = saleItems.get(1);
        SaleItem thirdItem = saleItems.get(2);

        assertEquals(3, sale.getSaleItems().size());

        assertEquals(1, firstItem.getItem().getId());
        assertEquals(34, firstItem.getQuantity());
        assertTrue((BigDecimal.valueOf(10).compareTo(firstItem.getItem().getPrice()) == 0));

        assertEquals(2, secondItem.getItem().getId());
        assertEquals(33, secondItem.getQuantity());
        assertTrue((BigDecimal.valueOf(1.50).compareTo(secondItem.getItem().getPrice()) == 0));

        assertEquals(3, thirdItem.getItem().getId());
        assertEquals(40, thirdItem.getQuantity());
        assertTrue((BigDecimal.valueOf(0.10).compareTo(thirdItem.getItem().getPrice()) == 0));
    }

    @Test
    public void saveWithWrongNumberOfAttributes() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> attributes = Arrays.asList("1111");
            this.converter.fromStringList(attributes);
        });
    }

}
