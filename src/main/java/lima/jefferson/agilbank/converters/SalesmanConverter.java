package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Salesman;

import java.math.BigDecimal;
import java.util.List;

public class SalesmanConverter implements Converter<Salesman> {

    @Override
    public Salesman fromStringList(List<String> attributes) {
        this.validateAttributes(attributes);

        Salesman salesman = new Salesman();

        salesman.setCpf(attributes.get(0));
        salesman.setName(attributes.get(1));
        salesman.setSalary(new BigDecimal(attributes.get(2)));

        return salesman;
    }

    private void validateAttributes(List<String> attributes) {
        if (attributes.size() != 3) {
            throw new IllegalArgumentException("Wrong number of attributes found");
        }
    }
}
