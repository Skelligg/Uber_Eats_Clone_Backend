package be.kdg.prog6.ordering.adaptor.in.response;

public record AddressDto(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
