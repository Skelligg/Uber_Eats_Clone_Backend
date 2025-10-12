package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.restaurant.adaptor.in.request.CreateDishDraftRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.EditDishRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.DishDto;
import be.kdg.prog6.restaurant.adaptor.in.response.DishVersionDto;
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
    private final EditDishUseCase editDishUseCase;

    public RestaurantController(
            DefaultCreateRestaurantUseCase createRestaurantUseCase,
            CreateDishDraftUseCase createDishDraftUseCase,
            PublishDishUseCase publishDishUseCase,
            UnpublishDishUseCase unpublishDishUseCase,
            EditDishUseCase editDishUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.createDishDraftUseCase = createDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.editDishUseCase = editDishUseCase;
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

        Restaurant created = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(
                created.getRestaurantId().id().toString(),
                created.getName()
        ));
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

        DishVersionDto draftDto = created.getDraftVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        return ResponseEntity.ok(new DishDto(
                created.getDishId().id(),
                null,          // no published version yet
                draftDto,
                created.getState().name()
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

        DishVersionDto publishedDto = published.getPublishedVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        return ResponseEntity.ok(new DishDto(
                published.getDishId().id(),
                publishedDto,
                null,
                published.getState().name()
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

        DishVersionDto draftDto = unpublished.getDraftVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        return ResponseEntity.ok(new DishDto(
                unpublished.getDishId().id(),
                null,
                draftDto,
                unpublished.getState().name()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}")
    public ResponseEntity<DishDto> editDish(
            @PathVariable String restaurantId,
            @PathVariable String dishId,
            @RequestBody EditDishRequest request) {
        EditDishCommand command = new EditDishCommand(
                DishId.of(UUID.fromString(dishId)),
                RestaurantId.of(UUID.fromString(restaurantId)),
                request.name(),
                request.description(),
                Price.of(request.price()),
                request.pictureUrl(),
                request.tags(),
                request.dishType()
        );

        Dish editedDish = editDishUseCase.editDish(command);

        DishVersionDto publishedDto = editedDish.getPublishedVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        DishVersionDto draftDto = editedDish.getDraftVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        return ResponseEntity.ok(new DishDto(
                editedDish.getDishId().id(),
                publishedDto,
                draftDto,
                editedDish.getState().name()
        ));
    }
}
