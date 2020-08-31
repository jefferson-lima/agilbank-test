package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Client;
import java.util.List;

public class ClientConverter implements Converter<Client> {

    @Override
    public Client fromStringList(List<String> attributes) {
        this.validateAttributes(attributes);

        Client client = new Client();

        client.setCnpj(attributes.get(0));
        client.setName(attributes.get(1));
        client.setBusinessArea(attributes.get(2));

        return client;
    }

    private void validateAttributes(List<String> attributes) {
        if (attributes.size() != 3) {
            throw new IllegalArgumentException("Wrong number of attributes found");
        }
    }
}
