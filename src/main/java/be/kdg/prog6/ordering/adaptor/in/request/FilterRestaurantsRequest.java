package be.kdg.prog6.ordering.adaptor.in.request;

public record FilterRestaurantsRequest(
        String cuisineType,
        String priceRange
) {
}
