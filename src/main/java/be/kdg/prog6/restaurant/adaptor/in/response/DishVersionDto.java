package be.kdg.prog6.restaurant.adaptor.in.response;

public record DishVersionDto(
        String name,
        String description,
        double price,
        String pictureUrl,
        String tags,
        String dishType
) {
}
