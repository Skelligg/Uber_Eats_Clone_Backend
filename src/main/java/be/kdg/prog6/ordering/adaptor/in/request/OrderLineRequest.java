package be.kdg.prog6.ordering.adaptor.in.request;

public record OrderLineRequest (
        String dishId,
        String dishName,
        int quantity,
        double unitPrice,
        double linePrice
){
}
