package be.kdg.prog6.restaurant.domain.vo.dish;

import be.kdg.prog6.common.vo.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.Price;

import java.util.Objects;

public record DishVersion(
        String name,
        String description,
        Price price,
        String pictureUrl,
        String tags,
        DISH_TYPE dishType
) {

    public DishVersion update(String name, String description, Price price,  String pictureUrl, String tags,  DISH_TYPE dishType) {
        return new DishVersion(
                name != null ? name : this.name,
                description != null ? description : this.description,
                price != null ? price : this.price,
                pictureUrl != null ? pictureUrl : this.pictureUrl,
                tags != null ? tags : this.tags,
                dishType != null ? dishType : this.dishType
        );
    }
}
