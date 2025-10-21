package be.kdg.prog6.ordering.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.ordering.domain.vo.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Order aggregate representing a customer's confirmed order.
 *
 * - Immutable order contents after placement (items, prices, totals).
 * - Lifecycle: PLACED -> (ACCEPTED | REJECTED | AUTO_DECLINED) -> READY -> PICKED_UP -> DELIVERED
 * - Publishes domain events (collected in domainEvents list).
 *
 * Note: method bodies intentionally left empty per request; implement business rules inside them.
 */
public class Order {
    // Identity
    private final OrderId orderId;
    private final RestaurantId restaurantId;

    // Snapshot of what was ordered (immutable after creation)
    private final List<OrderLine> lines;
    private final Money totalPrice;

    // Customer & delivery
    private final CustomerInfo customer;
    private final Address deliveryAddress;

    // Timestamps
    private final LocalDateTime placedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime readyAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    // State
    private ORDER_STATUS status;
    private String rejectionReason; // optional when REJECTED

    // Order decision window (five minutes)
    private final Duration restaurantDecisionWindow = Duration.ofMinutes(5);

    // Domain events collected by aggregate
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // Optional: estimated delivery time snapshot (in minutes) at placement
    private int estimatedDeliveryMinutes;

    // Optional: courier location updates (if tracked)
    private final List<CourierLocation> courierLocations = new ArrayList<>();

    // ---------- Constructors / Factory ----------
    public Order(
            OrderId orderId,
            RestaurantId restaurantId,
            List<OrderLine> lines,
            Money totalPrice,
            CustomerInfo customer,
            Address deliveryAddress,
            LocalDateTime placedAt
    ) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.lines = Collections.unmodifiableList(new ArrayList<>(lines));
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.placedAt = placedAt;
        this.status = ORDER_STATUS.PLACED;
    }

    public Order(OrderId orderId, RestaurantId restaurantId, List<OrderLine> lines, Money totalPrice, CustomerInfo customer, Address deliveryAddress, LocalDateTime placedAt, LocalDateTime acceptedAt, LocalDateTime rejectedAt, LocalDateTime readyAt, LocalDateTime pickedUpAt, LocalDateTime deliveredAt, ORDER_STATUS status, String rejectionReason, int estimatedDeliveryMinutes) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.lines = lines;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.placedAt = placedAt;
        this.acceptedAt = acceptedAt;
        this.rejectedAt = rejectedAt;
        this.readyAt = readyAt;
        this.pickedUpAt = pickedUpAt;
        this.deliveredAt = deliveredAt;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.estimatedDeliveryMinutes = estimatedDeliveryMinutes;
    }

    // ---------- Domain behaviors (methods left intentionally empty) ----------

    /**
     * Accept the order by the restaurant.
     * Should:
     * - only allow when status == PLACED
     * - set acceptedAt and status = ACCEPTED
     * - publish OrderAcceptedEvent (routing key restaurant.{id}.order.accepted.v1)
     */
    public void accept(String acceptedBy, LocalDateTime when) {
    }

    /**
     * Reject the order, with an optional reason.
     * Should:
     * - only allow when status == PLACED
     * - set rejectedAt and status = REJECTED
     * - publish OrderRejectedEvent
     */
    public void reject(String reason, LocalDateTime when) {
    }

    /**
     * Automatically decline the order if the restaurant decision window (5 minutes) has elapsed.
     * Should:
     * - be callable by a scheduler that periodically checks open orders
     * - set status = AUTO_DECLINED and publish OrderAutoDeclinedEvent
     */
    public void autoDeclineIfTimedOut(LocalDateTime now) {
    }

    /**
     * Mark an accepted order as ready for pickup by the kitchen.
     * Should:
     * - only allow when status == ACCEPTED
     * - set readyAt and status = READY
     * - publish OrderReadyForPickupEvent (routing key restaurant.{id}.order.ready.v1)
     */
    public void markReadyForPickup(LocalDateTime when) {
    }

    /**
     * Mark an order as picked up by the courier (consumed from delivery service).
     * Should:
     * - only allow when status == READY (or perhaps ACCEPTED depending on design)
     * - set pickedUpAt and status = PICKED_UP
     * - publish OrderPickedUpEvent (this event may be produced by delivery service; your system needs to handle inbound messages)
     */
    public void markPickedUp(LocalDateTime when, String courierId) {
    }

    /**
     * Mark an order as delivered. Should:
     * - set deliveredAt and status = DELIVERED
     * - publish OrderDeliveredEvent
     */
    public void markDelivered(LocalDateTime when) {
    }


    /**
     * Add a domain event to the internal list.
     */
    public void addDomainEvent(DomainEvent event) {
    }

    /**
     * Return and clear events (useful for publishers).
     */
    public List<DomainEvent> pullDomainEvents() {
        return null;
    }

    // ---------- Queries / Getters ----------

    public OrderId getOrderId() {
        return orderId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public Optional<LocalDateTime> getAcceptedAt() {
        return Optional.ofNullable(acceptedAt);
    }

    public Optional<LocalDateTime> getRejectedAt() {
        return Optional.ofNullable(rejectedAt);
    }

    public Optional<LocalDateTime> getReadyAt() {
        return Optional.ofNullable(readyAt);
    }

    public Optional<LocalDateTime> getPickedUpAt() {
        return Optional.ofNullable(pickedUpAt);
    }

    public Optional<LocalDateTime> getDeliveredAt() {
        return Optional.ofNullable(deliveredAt);
    }

    public ORDER_STATUS getStatus() {
        return status;
    }

    public Optional<String> getRejectionReason() {
        return Optional.ofNullable(rejectionReason);
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public int getEstimatedDeliveryMinutes() {
        return estimatedDeliveryMinutes;
    }

    public List<CourierLocation> getCourierLocations() {
        return courierLocations;
    }


    // ---------- Equals / HashCode (based on identity) ----------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order other = (Order) o;
        return Objects.equals(orderId, other.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

}
