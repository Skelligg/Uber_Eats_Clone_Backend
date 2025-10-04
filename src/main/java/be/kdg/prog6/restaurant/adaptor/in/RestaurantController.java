package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.restaurant.core.DefaultCreateRestaurantUseCase;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.*;
import be.kdg.prog6.restaurant.port.in.CreateRestaurantCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class RestaurantController {
    private final DefaultCreateRestaurantUseCase createRestaurantUseCase;

    public RestaurantController(DefaultCreateRestaurantUseCase createRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        CreateRestaurantCommand command = new CreateRestaurantCommand(
                OwnerId.MICHAEL,
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
                new OpeningHours(request.openingTime(), request.closingTime(),request.openDays())
        );

        Restaurant created = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(created.getRestaurantId().id().toString(),created.getName()));
    }
}
