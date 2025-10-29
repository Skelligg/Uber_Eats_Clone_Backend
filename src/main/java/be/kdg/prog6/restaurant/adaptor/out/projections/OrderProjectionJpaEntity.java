package be.kdg.prog6.restaurant.adaptor.out.projections;

import be.kdg.prog6.common.vo.ORDER_STATUS;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_projections", schema = "restaurant")
public class OrderProjectionJpaEntity {

    @Id
    private UUID orderId;

    @Column(nullable = false)
    private UUID restaurantId;

    // Delivery address flattened
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    @Column(nullable = false)
    private double totalPrice;

    private LocalDateTime placedAt;

    @Enumerated(EnumType.STRING)
    private ORDER_STATUS status;

    private String rejectionReason;

    private LocalDateTime acceptedAt;
    private LocalDateTime readyAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    @ElementCollection
    @CollectionTable(
            name = "order_projection_lines",
            schema = "restaurant",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderLineProjectionEmbeddable> lines;

    protected OrderProjectionJpaEntity(UUID orderId,
                                       UUID restaurantId,
                                       String street, String number, String postalCode, String city, String country,
                                       double totalPrice,
                                       LocalDateTime placedAt, ORDER_STATUS status, String rejectionReason, LocalDateTime acceptedAt, LocalDateTime readyAt, LocalDateTime rejectedAt, LocalDateTime pickedUpAt, LocalDateTime deliveredAt, List<OrderLineProjectionEmbeddable> lineEntities) {
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
        this.rejectionReason = rejectionReason;
        this.acceptedAt = acceptedAt;
        this.readyAt = readyAt;
        this.rejectedAt = rejectedAt;
        this.pickedUpAt = pickedUpAt;
        this.deliveredAt = deliveredAt;
        this.lines = lineEntities;
    }

    public OrderProjectionJpaEntity(UUID orderId, UUID restaurantId, String customerName, String customerEmail,
                                    String street, String number, String postalCode, String city, String country,
                                    double totalPrice, LocalDateTime placedAt, ORDER_STATUS status,
                                    String rejectionReason, LocalDateTime acceptedAt, LocalDateTime readyAt,
                                    LocalDateTime rejectedAt, LocalDateTime pickedUpAt, LocalDateTime deliveredAt,
                                    List<OrderLineProjectionEmbeddable> lines) {
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
        this.rejectionReason = rejectionReason;
        this.acceptedAt = acceptedAt;
        this.readyAt = readyAt;
        this.rejectedAt = rejectedAt;
        this.pickedUpAt = pickedUpAt;
        this.deliveredAt = deliveredAt;
        this.lines = lines;
    }

    public OrderProjectionJpaEntity() {

    }

    // --- Getters ---
    public UUID getOrderId() { return orderId; }
    public UUID getRestaurantId() { return restaurantId; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getPlacedAt() { return placedAt; }
    public ORDER_STATUS getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }
    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public LocalDateTime getReadyAt() { return readyAt; }
    public LocalDateTime getRejectedAt() { return rejectedAt; }
    public LocalDateTime getPickedUpAt() { return pickedUpAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public List<OrderLineProjectionEmbeddable> getLines() { return lines; }
}
