package be.kdg.prog6.restaurant.domain.vo;

public record EmailAddress (
        String emailAddress
    ) {
    public EmailAddress {
        if (!emailAddress.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
