package lima.jefferson.agilbank.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    @Test
    public void getIdShouldReturnTheCnpj() {
        Client client = new Client();
        client.setCnpj("123123123");

        assertEquals(client.getCnpj(), client.getId());
    }
}
