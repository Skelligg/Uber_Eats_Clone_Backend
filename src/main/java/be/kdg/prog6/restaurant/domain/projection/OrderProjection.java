package be.kdg.prog6.restaurant.domain.projection;

import be.kdg.prog6.common.vo.ORDER_STATUS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderProjection {

    private UUID orderId;
    private UUID restaurantId;

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    private double totalPrice;
    private LocalDateTime placedAt;

    private ORDER_STATUS status;

    private String rejectionReason;

    private LocalDateTime acceptedAt;
    private LocalDateTime readyAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    private List<OrderLineProjection> lines;

    public OrderProjection() {
    }

    public OrderProjection(UUID orderId, UUID restaurantId,
                           String street, String number, String postalCode, String city, String country,
                           double totalPrice, LocalDateTime placedAt, ORDER_STATUS status,
                           List<OrderLineProjection> lines) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.totalPrice = totalPrice;
        this.placedAt = placedAt;
        this.status = status;
        this.lines = lines;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public ORDER_STATUS getStatus() {
        return status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public LocalDateTime getReadyAt() {
        return readyAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public LocalDateTime getPickedUpAt() {
        return pickedUpAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public List<OrderLineProjection> getLines() {
        return lines;
    }
}
