package lima.jefferson.agilbank.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesmanTest {

    @Test
    public void getIdShouldReturnTheCpf() {
        Salesman salesman = new Salesman();
        salesman.setCpf("123123123");

        assertEquals(salesman.getCpf(), salesman.getId());
    }
}
