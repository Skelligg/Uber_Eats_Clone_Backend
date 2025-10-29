package be.kdg.prog6.ordering.adaptor.out.order;

import be.kdg.prog6.common.vo.ORDER_STATUS;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "ordering")
public class OrderJpaEntity {

    @Id
    private UUID id;

    // Reference to restaurant
    @Column(nullable = false)
    private UUID restaurantId;

    // Customer info (flattened)
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    // Delivery address (flattened)
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    // Order lines (each line is an embeddable)
    @ElementCollection
    @CollectionTable(name = "order_lines", schema = "ordering", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineEmbeddable> lines;

    private double totalPrice;

    // Status
    @Enumerated(EnumType.STRING)
    private ORDER_STATUS status;

    // Rejection reason (nullable)
    private String rejectionReason;

    @ElementCollection
    @CollectionTable(
            name = "courier_locations",
            schema = "ordering",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<CourierLocationEmbeddable> courierLocations;

    private LocalDateTime placedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime readyAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    private Integer estimatedDeliveryMinutes;

    private String paymentSessionId;

    protected OrderJpaEntity() {
    }

    public OrderJpaEntity(
            UUID id,
            UUID restaurantId,
            String customerName,
            String customerEmail,
            String street,
            String number,
            String postalCode,
            String city,
            String country,
            List<OrderLineEmbeddable> lines,
            double totalPrice,
            ORDER_STATUS status,
            String rejectionReason,
            LocalDateTime placedAt,
            LocalDateTime acceptedAt,
            LocalDateTime rejectedAt,
            LocalDateTime readyAt,
            LocalDateTime pickedUpAt,
            LocalDateTime deliveredAt,
            Integer estimatedDeliveryMinutes,
            List<CourierLocationEmbeddable> courierLocations,
            String paymentSessionId
    ) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.lines = lines;
        this.totalPrice = totalPrice;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.placedAt = placedAt;
        this.acceptedAt = acceptedAt;
        this.rejectedAt = rejectedAt;
        this.readyAt = readyAt;
        this.pickedUpAt = pickedUpAt;
        this.deliveredAt = deliveredAt;
        this.estimatedDeliveryMinutes = estimatedDeliveryMinutes;
        this.courierLocations = courierLocations;
        this.paymentSessionId = paymentSessionId;
    }

    // --- Getters ---
    public UUID getId() {
        return id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
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

    public List<CourierLocationEmbeddable> getCourierLocations() {
        return courierLocations;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public ORDER_STATUS getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderLineEmbeddable> getLines() {
        return lines;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public LocalDateTime getReadyAt() {
        return readyAt;
    }

    public LocalDateTime getPickedUpAt() {
        return pickedUpAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public Integer getEstimatedDeliveryMinutes() {
        return estimatedDeliveryMinutes;
    }

    public String getPaymentSessionId() {
        return paymentSessionId;
    }
}