package be.kdg.prog6.ordering.domain.vo;

public record OrderLine(
        DishId dishId,
        String dishName,
        int quantity,
        Money unitPrice,
        Money linePrice
) { }
