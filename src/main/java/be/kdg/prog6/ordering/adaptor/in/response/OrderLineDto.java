package be.kdg.prog6.ordering.adaptor.in.response;

public record OrderLineDto (
        String dishId,
        String dishName,
        int quantity,
        double unitPrice,
        double linePrice
){
}
