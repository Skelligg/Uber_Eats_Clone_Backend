package be.kdg.prog6.restaurant.adaptor.in.response;

import be.kdg.prog6.restaurant.domain.vo.dish.DishId;

import java.util.UUID;

public record OrderlineDto (
        UUID dishId,
        String dishName,
        int quantity,
        double unitPrice,
        double linePrice
){
}
