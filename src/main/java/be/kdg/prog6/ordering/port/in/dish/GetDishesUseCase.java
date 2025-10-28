package be.kdg.prog6.ordering.port.in.dish;

import be.kdg.prog6.ordering.adaptor.in.request.FilterDishesRequest;
import be.kdg.prog6.ordering.domain.projection.DishProjection;

import java.util.List;
import java.util.UUID;

public interface GetDishesUseCase {
    List<DishProjection> getDishes(FilterDishesRequest request);
}
