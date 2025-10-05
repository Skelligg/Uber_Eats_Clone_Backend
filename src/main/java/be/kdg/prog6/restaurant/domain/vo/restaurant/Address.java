package be.kdg.prog6.restaurant.domain.vo.restaurant;

public record Address (
        String street,
        String number,
        String postalCode,
        String city,
        String country
    ){
    public Address {
        if (street == null || number == null || postalCode == null || city == null || country == null) {
            throw new IllegalArgumentException("Invalid Address");
        }
    }

    public static Address of(String street, String number, String postalCode, String city, String country) {
        return new Address(street, number, postalCode, city, country);
    }
}
