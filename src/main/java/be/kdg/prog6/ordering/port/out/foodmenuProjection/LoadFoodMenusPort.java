package be.kdg.prog6.ordering.port.out.foodmenuProjection;

import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;

import java.util.List;

public interface LoadFoodMenusPort {
    List<FoodMenuProjection> loadAll();

}
