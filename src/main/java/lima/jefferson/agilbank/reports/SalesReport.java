package lima.jefferson.agilbank.reports;

import lima.jefferson.agilbank.entities.Client;
import lima.jefferson.agilbank.entities.Entity;
import lima.jefferson.agilbank.entities.Sale;
import lima.jefferson.agilbank.entities.Salesman;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SalesReport {
    private int clientsNumber;
    private int salesmenNumber;
    private Long mostExpensiveSale;
    private String worstSalesman;

    private List<Entity> entities;

    public SalesReport(List<Entity> entities) {
        this.entities = entities;

        this.clientsNumber = this.countRecordsByType(Client.class);
        this.salesmenNumber = this.countRecordsByType(Salesman.class);
        this.mostExpensiveSale = this.getMostExpensiveSaleId();
        this.worstSalesman = this.getWorstSalesman();
    }

    public List<?> getEntitiesByType(Class<?> typeClass) {
        return this.entities.stream()
                .filter(entity -> entity.getClass().equals(typeClass))
                .collect(Collectors.toList());
    }

    public int countRecordsByType(Class<?> typeClass) {
        return this.getEntitiesByType(typeClass).size();
    }

    public Long getMostExpensiveSaleId() {
        return this.entities
                .stream()
                .filter(entity -> entity instanceof Sale)
                .map(entity -> (Sale) entity)
                .max(Comparator.comparing(Sale::getTotalPrice))
                .map(Sale::getId)
                .orElse(null);
    }

    public String getWorstSalesman() {
        List<Salesman> allSalesmen = (List<Salesman>) this.getEntitiesByType(Salesman.class);

        HashMap<Salesman, BigDecimal> salesBySalesman = new HashMap<>();

        for (Salesman salesman: allSalesmen) {
            salesBySalesman.put(salesman, this.getTotalOfSalesBySalesman(salesman));
        }

        return salesBySalesman.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .map(Salesman::getName)
                .orElse("");
    }

    public BigDecimal getTotalOfSalesBySalesman(Salesman salesman) {
        return this.getEntitiesByType(Sale.class)
                .stream()
                .map(e -> (Sale)e)
                .filter(sale -> sale.getSalesmanName().equals(salesman.getName()))
                .map(Sale::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "Number of clients: " + this.clientsNumber + "\n"
             + "Number of salesmen: " + this.salesmenNumber + "\n"
             + "Most expensive sale: " + this.mostExpensiveSale + "\n"
             + "Worst salesman: " + this.worstSalesman + "\n";
    }
}
