package be.kdg.prog6.ordering.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.ORDER_STATUS;
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

    public void accepted() {
        if(ORDER_STATUS.PLACED == status) {status = ORDER_STATUS.ACCEPTED;}
        acceptedAt = LocalDateTime.now();
    }

    public void rejected(String reason) {
        if(ORDER_STATUS.PLACED == status) {status = ORDER_STATUS.REJECTED;}
        rejectionReason = reason;
        rejectedAt = LocalDateTime.now();
    }

    public void autoDeclineIfTimedOut(LocalDateTime now) {
    }

    public void markReadyForPickup() {
        if(ORDER_STATUS.ACCEPTED == status) {status = ORDER_STATUS.READY;}
        readyAt = LocalDateTime.now();
    }

    public void pickedUp() {
        if(ORDER_STATUS.READY == status) {status = ORDER_STATUS.PICKED_UP;}
        if (pickedUpAt == null){
            pickedUpAt = LocalDateTime.now();
        }
    }

    public void markDelivered() {
        if(ORDER_STATUS.PICKED_UP == status) {status = ORDER_STATUS.DELIVERED;}
        deliveredAt = LocalDateTime.now();
    }

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

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
