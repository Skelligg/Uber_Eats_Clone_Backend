package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.ordering.adaptor.in.response.RestaurantProjectionDto;
import be.kdg.prog6.ordering.port.in.GetRestaurantsUseCase;
import be.kdg.prog6.ordering.port.in.RestaurantsChangedProjector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerRestaurantController {
    private final GetRestaurantsUseCase getRestaurantsUseCase;

    public CustomerRestaurantController(GetRestaurantsUseCase getRestaurantsUseCase) {
        this.getRestaurantsUseCase = getRestaurantsUseCase;
    }

    @GetMapping("/customer/restaurants")
    public ResponseEntity<List<RestaurantProjectionDto>> getRestaurants(
            @RequestParam(required = false) String cuisineType,
            @RequestParam(required = false) boolean onlyOpen) {

        List<RestaurantProjectionDto> dtos = getRestaurantsUseCase.getRestaurants(
                        Optional.ofNullable(cuisineType),
                        Optional.of(onlyOpen)
                ).stream()
                .map(r -> new RestaurantProjectionDto(
                        r.getRestaurantId(),
                        r.getOwnerId(),
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
                        r.getClosingTime()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}

// this is completely in the orderingcontext, the usecase is in core and references the shadow restuarant in the read only db
// the way the db is updated is by an event, and the events are listened to by