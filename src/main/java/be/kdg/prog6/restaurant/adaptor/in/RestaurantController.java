package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.restaurant.adaptor.in.request.CreateDishDraftRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.DishDto;
import be.kdg.prog6.restaurant.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.restaurant.core.DefaultCreateRestaurantUseCase;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;
import be.kdg.prog6.restaurant.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final DefaultCreateRestaurantUseCase createRestaurantUseCase;
    private final CreateDishDraftUseCase createDishDraftUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;

    public RestaurantController(DefaultCreateRestaurantUseCase createRestaurantUseCase, CreateDishDraftUseCase createDishDraftUseCase, PublishDishUseCase publishDishUseCase, UnpublishDishUseCase unpublishDishUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.createDishDraftUseCase = createDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
    }

    @PostMapping()
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
                new OpeningHours(request.openingTime(), request.closingTime(),request.openDays())
        );

        Restaurant created = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(created.getRestaurantId().id().toString(),created.getName()));
    }
    
    @PostMapping("/dishes")
    public ResponseEntity<DishDto> addDish(@RequestBody CreateDishDraftRequest request) {
        CreateDishDraftCommand command = new CreateDishDraftCommand(
                new RestaurantId(UUID.fromString(request.restaurantId())),
                request.name(),
                request.description(),
                Price.of(request.price()),
                request.pictureUrl(), 
                request.tags(),
                request.dishType()
        );
        
        Dish created = createDishDraftUseCase.createDishDraftForFoodMenu(command);
        return ResponseEntity.ok(new DishDto(
                created.getDraftVersion().orElseThrow().name(),
                created.getDraftVersion().orElseThrow().description(),
                created.getDraftVersion().orElseThrow().price().asDouble(),
                created.getDraftVersion().orElseThrow().pictureUrl(),
                created.getDraftVersion().orElseThrow().tags(),
                created.getDraftVersion().orElseThrow().dishType().toString()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}/publish")
    public ResponseEntity<DishDto> publishDish(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        PublishingDishCommand command = new PublishingDishCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                DishId.of(UUID.fromString(dishId))
        );

        Dish published = publishDishUseCase.publishDish(command);

        return ResponseEntity.ok(new DishDto(
                published.getPublishedVersion().orElseThrow().name(),
                published.getPublishedVersion().orElseThrow().description(),
                published.getPublishedVersion().orElseThrow().price().asDouble(),
                published.getPublishedVersion().orElseThrow().pictureUrl(),
                published.getPublishedVersion().orElseThrow().tags(),
                published.getPublishedVersion().orElseThrow().dishType().toString()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}/unpublish")
    public ResponseEntity<DishDto> unpublishDish(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        PublishingDishCommand command = new PublishingDishCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                DishId.of(UUID.fromString(dishId))
        );

        Dish unpublished = unpublishDishUseCase.unpublishDish(command);

        return ResponseEntity.ok(new DishDto(
                unpublished.getDraftVersion().orElseThrow().name(),
                unpublished.getDraftVersion().orElseThrow().description(),
                unpublished.getDraftVersion().orElseThrow().price().asDouble(),
                unpublished.getDraftVersion().orElseThrow().pictureUrl(),
                unpublished.getDraftVersion().orElseThrow().tags(),
                unpublished.getDraftVersion().orElseThrow().dishType().toString()
        ));
    }

}
