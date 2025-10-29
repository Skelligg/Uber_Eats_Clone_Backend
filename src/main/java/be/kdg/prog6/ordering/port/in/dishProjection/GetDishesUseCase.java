package be.kdg.prog6.ordering.port.in.dishProjection;

import be.kdg.prog6.ordering.adaptor.in.request.FilterDishesRequest;
import be.kdg.prog6.ordering.domain.projection.DishProjection;

import java.util.List;

public interface GetDishesUseCase {
    List<DishProjection> getDishes(FilterDishesRequest request);
}
