package lima.jefferson.agilbank.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class Item extends Entity {
    private Long id;
    private BigDecimal price;
}
