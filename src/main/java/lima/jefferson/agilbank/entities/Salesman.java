package lima.jefferson.agilbank.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class Salesman extends Entity {

    private String cpf;
    private String name;
    private BigDecimal salary;

    @Override
    public String getId() {
        return this.cpf;
    }

}
