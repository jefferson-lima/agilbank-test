package lima.jefferson.agilbank.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends Entity {

    private String cnpj;
    private String name;
    private String businessArea;

    @Override
    public String getId() {
        return this.cnpj;
    }
}
