package lima.jefferson.agilbank.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleTest {

    @Test
    public void getTotalPrice() {
        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        SaleItem s1 = new SaleItem();
        s1.setQuantity(5);
        s1.setItem(item);

        SaleItem s2 = new SaleItem();
        s2.setQuantity(1);
        s2.setItem(item);

        Sale sale = new Sale();
        sale.setSaleItems(Arrays.asList(s1, s2));

        assertEquals(new BigDecimal(60), sale.getTotalPrice());
    }

}
