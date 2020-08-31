package lima.jefferson.agilbank.converters;

import java.util.List;

public interface Converter<E> {
    E fromStringList(List<String> attributes);
}
