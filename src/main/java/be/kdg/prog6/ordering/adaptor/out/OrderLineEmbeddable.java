package be.kdg.prog6.ordering.adaptor.out;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class OrderLineEmbeddable {

    private UUID dishId;
    private String dishName;
    private int quantity;
    private double unitPrice;
    private double linePrice;

    public OrderLineEmbeddable() {}

    public OrderLineEmbeddable(UUID dishId, String dishName, int quantity, double unitPrice, double linePrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.linePrice = linePrice;
    }

    public UUID getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLinePrice() {
        return linePrice;
    }
}
