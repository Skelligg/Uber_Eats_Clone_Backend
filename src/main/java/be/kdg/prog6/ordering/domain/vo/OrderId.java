package be.kdg.prog6.ordering.domain.vo;

import java.util.UUID;

public record OrderId (
     UUID id)
{
    public OrderId(UUID id) { this.id = id; }
    public UUID id() { return id; }
    public static OrderId newId() { return new OrderId(UUID.randomUUID()); }
}
