package be.kdg.prog6.common.vo;
import java.util.UUID;

public record OrderLineEventInfo(
        UUID dishId,
        String dishName,
        int quantity,
        double unitPrice,
        double linePrice
) {
}
