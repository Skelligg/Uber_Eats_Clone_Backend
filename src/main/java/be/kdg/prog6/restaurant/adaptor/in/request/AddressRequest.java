package be.kdg.prog6.restaurant.adaptor.in.request;

public record AddressRequest (
    String street,
    String number,
    String postalCode,
    String city,
    String country
){
}
