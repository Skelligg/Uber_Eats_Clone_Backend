package be.kdg.prog6.restaurant.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object representing a price in euros.
 * Immutable and comparable by value.
 */
public record Price(BigDecimal amount) {

    public Price {
        Objects.requireNonNull(amount, "Price amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public static Price of(double value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public static Price of(BigDecimal value) {
        return new Price(value);
    }

    public Price add(Price other) {
        return new Price(this.amount.add(other.amount));
    }

    public Price subtract(Price other) {
        return new Price(this.amount.subtract(other.amount));
    }

    public Price multiply(double factor) {
        return new Price(this.amount.multiply(BigDecimal.valueOf(factor)));
    }

    public boolean isGreaterThan(Price other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThan(Price other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public double asDouble() {
        return amount.doubleValue();
    }

    @Override
    public String toString() {
        return "€" + amount;
    }
}
