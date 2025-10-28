package be.kdg.prog6.ordering.port.in.order;

import java.util.UUID;

public record OrderDeliveredCommand(
        UUID orderId
) {
}
