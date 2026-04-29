package models;

import java.util.Objects;

public class Price {
    private final int value;
    private final Currency currency;

    public Price(int value, Currency currency){
        this.value = value;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return value == price.value && currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}
