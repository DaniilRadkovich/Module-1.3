package main.com.radkovich.module_1_3.repository.gson;

import java.util.List;
import java.util.function.Function;

public class IdGenerator {
    public static <T> long generateNextId(List<T> items, Function<T, Long> idGenerator) {
        return items.stream()
                .mapToLong(idGenerator::apply)
                .max().orElse(0) + 1;
    }
}
