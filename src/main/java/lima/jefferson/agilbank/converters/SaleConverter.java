package lima.jefferson.agilbank.converters;

import lima.jefferson.agilbank.entities.Item;
import lima.jefferson.agilbank.entities.Sale;
import lima.jefferson.agilbank.entities.SaleItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaleConverter implements Converter<Sale> {

    @Override
    public Sale fromStringList(List<String> attributes) {
        this.validateAttributes(attributes);

        Sale sale = new Sale();

        sale.setId(Long.parseLong(attributes.get(0)));
        sale.setSaleItems(this.convertSaleItemsListFromString(attributes.get(1)));
        sale.setSalesmanName(attributes.get(2));

        return sale;
    }

    private List<SaleItem> convertSaleItemsListFromString(String itemsListString) {
        String[] itemsStringArray = itemsListString.substring(1, itemsListString.length() - 1).split(",");

        return Arrays.stream(itemsStringArray)
                .map(this::convertSaleItemFromString)
                .collect(Collectors.toList());
    }

    private SaleItem convertSaleItemFromString(String saleItemString) {
        String[] saleItemArray = saleItemString.split("-");
        SaleItem saleItem = new SaleItem();

        saleItem.setQuantity(Integer.parseInt(saleItemArray[1]));

        Item item = new Item();
        item.setId(Long.parseLong(saleItemArray[0]));
        item.setPrice(new BigDecimal(saleItemArray[2]));

        saleItem.setItem(item);

        return saleItem;
    }

    private void validateAttributes(List<String> attributes) {
        if (attributes.size() != 3) {
            throw new IllegalArgumentException("Wrong number of attributes found");
        }
    }
}
