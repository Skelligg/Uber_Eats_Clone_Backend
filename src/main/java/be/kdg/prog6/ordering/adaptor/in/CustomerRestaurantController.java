package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.ordering.adaptor.in.request.FilterDishesRequest;
import be.kdg.prog6.ordering.adaptor.in.request.FilterRestaurantsRequest;
import be.kdg.prog6.ordering.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.ordering.port.in.dish.GetDishesUseCase;
import be.kdg.prog6.ordering.port.in.restaurant.GetRestaurantsUseCase;
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
    public ResponseEntity<List<RestaurantDto>> getRestaurants(@RequestParam(required = false) String cuisineType,
                                                              @RequestParam(required = false) String priceRange)
    {
        FilterRestaurantsRequest request = new FilterRestaurantsRequest(
                cuisineType != null ? cuisineType : "",
                priceRange != null ? priceRange : ""
        );

        List<RestaurantDto> dtos = getRestaurantsUseCase.getRestaurants(request).stream()
                .map(r -> new RestaurantDto(
                        r.getRestaurantId(),
                        r.getName(),
                        r.getStreet(),
                        r.getNumber(),
                        r.getPostalCode(),
                        r.getCity(),
                        r.getCountry(),
                        r.getEmailAddress(),
                        r.getPictures(),
                        r.getCuisineType().toString().toLowerCase(),
                        r.getMinPrepTime(),
                        r.getMaxPrepTime(),
                        r.getOpeningTime(),
                        r.getClosingTime(),
                        r.getOpenDays().stream()
                                .map(Enum::name)
                                .map(String::toLowerCase)
                                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                                .toList()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDto>> getDishes (
            @PathVariable UUID restaurantId,
            @RequestParam(required = false) String dishType,
            @RequestParam(required = false) String tags
    ) {
        FilterDishesRequest request = new FilterDishesRequest(
                restaurantId,
                dishType != null ? dishType : "",
                tags != null ? tags : ""
        );

        List<DishDto> dishes = getDishesUseCase.getDishes(request).stream()
                .map(d -> new DishDto(
                        d.getDishId(),
                        d.getFoodMenuId(),
                        d.getName(),
                        d.getDescription(),
                        d.getPrice(),
                        d.getPictureUrl(),
                        d.getTags(),
                        d.getDishType().toString().substring(0, 1).toUpperCase() + d.getDishType().toString().substring(1).toLowerCase(),
                        d.getDishState()
                        ))
                .toList();

        return ResponseEntity.ok(dishes);
    }
}

// this is completely in the orderingcontext, the usecase is in core and references the shadow restuarant in the read only db
// the way the db is updated is by an event, and the events are listened to by