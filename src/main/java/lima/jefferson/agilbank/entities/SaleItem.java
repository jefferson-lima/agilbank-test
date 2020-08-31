package lima.jefferson.agilbank.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItem {
    private int quantity;
    private Item item;

    public BigDecimal getTotalPrice() {
        return this.item.getPrice().multiply(new BigDecimal(this.quantity));
    }
}
