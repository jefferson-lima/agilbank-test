package lima.jefferson.agilbank.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleItemTest {

    @Test
    public void getTotalPrice() {
        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        SaleItem saleItem = new SaleItem();
        saleItem.setQuantity(5);
        saleItem.setItem(item);

        assertEquals(new BigDecimal(50), saleItem.getTotalPrice());
    }

}
