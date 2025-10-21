package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.restaurant.core.restaurant.DefaultCreateRestaurantUseCase;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;
import be.kdg.prog6.restaurant.port.in.restaurant.CreateRestaurantCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final DefaultCreateRestaurantUseCase createRestaurantUseCase;

    public RestaurantController(
            DefaultCreateRestaurantUseCase createRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        CreateRestaurantCommand command = new CreateRestaurantCommand(
                OwnerId.of(request.ownerId()),
                request.name(),
                new Address(
                        request.address().street(),
                        request.address().number(),
                        request.address().postalCode(),
                        request.address().city(),
                        request.address().country()
                ),
                new EmailAddress(request.emailAddress()),
                request.pictures().stream()
                        .map(Picture::new)
                        .collect(Collectors.toList()),
                CUISINE_TYPE.valueOf(request.cuisineType().toUpperCase()),
                new PrepTime(request.minPrepTime(), request.maxPrepTime()),
                new OpeningHours(request.openingTime(), request.closingTime(), request.openDays())
        );

        Restaurant restaurantCreated = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(
                restaurantCreated.getRestaurantId().id().toString(),
                restaurantCreated.getName()
        ));
    }

}
