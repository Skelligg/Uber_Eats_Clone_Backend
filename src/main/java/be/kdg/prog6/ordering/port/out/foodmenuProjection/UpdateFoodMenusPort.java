package be.kdg.prog6.ordering.port.out.foodmenuProjection;

import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;

public interface UpdateFoodMenusPort {
    void updateFoodMenus(FoodMenuProjection foodMenuProjection);

}
