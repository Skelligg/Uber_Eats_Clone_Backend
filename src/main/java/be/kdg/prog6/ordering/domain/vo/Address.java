package be.kdg.prog6.ordering.domain.vo;

public record Address(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) { }
