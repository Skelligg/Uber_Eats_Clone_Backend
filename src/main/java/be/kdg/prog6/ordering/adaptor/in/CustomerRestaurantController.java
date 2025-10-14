package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.ordering.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.ordering.port.in.GetDishesUseCase;
import be.kdg.prog6.ordering.port.in.GetRestaurantsUseCase;
import be.kdg.prog6.ordering.adaptor.in.response.DishDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/restaurants")
public class CustomerRestaurantController {
    private final GetRestaurantsUseCase getRestaurantsUseCase;
    private final GetDishesUseCase getDishesUseCase;

    public CustomerRestaurantController(GetRestaurantsUseCase getRestaurantsUseCase, GetDishesUseCase getDishesUseCase) {
        this.getRestaurantsUseCase = getRestaurantsUseCase;
        this.getDishesUseCase = getDishesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getRestaurants(
            @RequestParam(required = false) String cuisineType,
            @RequestParam(required = false) boolean onlyOpen) {

        List<RestaurantDto> dtos = getRestaurantsUseCase.getRestaurants(
                        Optional.ofNullable(cuisineType),
                        Optional.of(onlyOpen)
                ).stream()
                .map(r -> new RestaurantDto(
                        r.getRestaurantId(),
                        r.getOwnerId(),
                        r.getOwnerName(),
                        r.getName(),
                        r.getStreet(),
                        r.getNumber(),
                        r.getPostalCode(),
                        r.getCity(),
                        r.getCountry(),
                        r.getEmailAddress(),
                        r.getPictures(),
                        r.getCuisineType(),
                        r.getMinPrepTime(),
                        r.getMaxPrepTime(),
                        r.getOpeningTime(),
                        r.getClosingTime(),
                        r.getOpenDays()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDto>> getDishes (
            @PathVariable String restaurantId
    ) {
        List<DishDto> dishes = getDishesUseCase.getDishes(UUID.fromString(restaurantId)).stream()
                .map(d -> new DishDto(
                        d.getDishId(),
                        d.getFoodMenuId(),
                        d.getName(),
                        d.getDescription(),
                        d.getPrice(),
                        d.getPictureUrl(),
                        d.getTags(),
                        d.getDishType(),
                        d.getDishState()
                        ))
                .toList();

        return ResponseEntity.ok(dishes);
    }
}

// this is completely in the orderingcontext, the usecase is in core and references the shadow restuarant in the read only db
// the way the db is updated is by an event, and the events are listened to by