package be.kdg.prog6.ordering.domain.vo;

import java.math.BigDecimal;
import java.util.UUID;

public record Money(BigDecimal price) {
    public static Money of(double price) {
        return new Money(BigDecimal.valueOf(price));
    }
}
