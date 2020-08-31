package lima.jefferson.agilbank.parsing;

import lima.jefferson.agilbank.converters.ClientConverter;
import lima.jefferson.agilbank.converters.Converter;
import lima.jefferson.agilbank.converters.SaleConverter;
import lima.jefferson.agilbank.converters.SalesmanConverter;
import lima.jefferson.agilbank.entities.Entity;
import lima.jefferson.agilbank.records.Record;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class RecordProcessor implements ItemProcessor<Record, Entity> {
    @Override
    public Entity process(Record record) throws Exception {

        Converter<?> converter = this.getConverter(record);
        return (Entity) converter.fromStringList(record.getAttributes());
    }

    private Converter<?> getConverter(Record record) {
        Converter<?> converter;
        switch (record.getType()) {
            case SALE:
                converter = new SaleConverter();
                break;
            case SALESMAN:
                converter = new SalesmanConverter();
                break;
            case CLIENT:
                converter = new ClientConverter();
                break;
            default:
                throw new IllegalArgumentException("Unknown record type");
        }
        return converter;
    }
}
