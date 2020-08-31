package lima.jefferson.agilbank.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Sale extends Entity {

    private Long id;
    private List<SaleItem> saleItems;
    private String salesmanName;

    @Override
    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return this.getSaleItems()
                   .stream()
                   .map(SaleItem::getTotalPrice)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
