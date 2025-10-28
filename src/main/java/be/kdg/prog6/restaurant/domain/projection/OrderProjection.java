package be.kdg.prog6.restaurant.domain.projection;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.ORDER_STATUS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderProjection {

    private UUID orderId;
    private UUID restaurantId;

    private Address address;

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

    private final List<DomainEvent> domainEvents= new ArrayList<>();

    public OrderProjection() {
    }

    public OrderProjection(UUID orderId, UUID restaurantId, Address address, double totalPrice, LocalDateTime placedAt, ORDER_STATUS status, String rejectionReason, LocalDateTime acceptedAt, LocalDateTime readyAt, LocalDateTime rejectedAt, LocalDateTime pickedUpAt, LocalDateTime deliveredAt, List<OrderLineProjection> lines) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.address = address;
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

    public OrderProjection(UUID orderId, UUID restaurantId,
                           Address address,
                           double totalPrice, LocalDateTime placedAt, ORDER_STATUS status,
                           List<OrderLineProjection> lines) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.address = address;
        this.totalPrice = totalPrice;
        this.placedAt = placedAt;
        this.status = status;
        this.lines = lines;
    }

    public void accept(){
        status = ORDER_STATUS.ACCEPTED;
        acceptedAt = LocalDateTime.now();
    }

    public void markReadyForPickUp(){
        status = ORDER_STATUS.READY;
        readyAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        status = ORDER_STATUS.REJECTED;
        rejectedAt = LocalDateTime.now();
        rejectionReason = reason;
    }

    //getters & setters

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
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

    public Address getAddress() {
        return address;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }
}
