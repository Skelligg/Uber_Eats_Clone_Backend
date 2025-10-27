package be.kdg.prog6.common.vo;

public record RabbitMQAddress(
        String street,
        String number,
        String postalCode,
        String city
) {
    public static RabbitMQAddress from(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        return new RabbitMQAddress(
                address.street(),
                address.number(),
                address.postalCode(),
                address.city()
        );
    }
}
