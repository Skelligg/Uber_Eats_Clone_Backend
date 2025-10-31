package be.kdg.prog6.restaurant.adaptor.in.controller;

import be.kdg.prog6.common.vo.DISH_TYPE;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateDishDraftRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.EditDishRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.SchedulePublicationRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.DishDto;
import be.kdg.prog6.restaurant.adaptor.in.response.DishVersionDto;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.dish.*;
import be.kdg.prog6.restaurant.port.in.foodmenu.GetFoodMenuUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/foodmenus")
public class FoodMenuController {
    private final CreateDishDraftUseCase createDishDraftUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final EditDishUseCase editDishUseCase;
    private final MarkDishOutOfStockUseCase markDishOutOfStockUseCase;
    private final MarkDishAvailableUseCase markDishAvailableUseCase;
    private final ApplyPendingChangesUseCase applyPendingChangesUseCase;
    private final SchedulePendingChangesUseCase schedulePendingChangesUseCase;
    private final GetFoodMenuUseCase getFoodMenuUseCase;

    public FoodMenuController(CreateDishDraftUseCase createDishDraftUseCase, PublishDishUseCase publishDishUseCase, UnpublishDishUseCase unpublishDishUseCase, EditDishUseCase editDishUseCase, MarkDishOutOfStockUseCase markDishOutOfStockUseCase, MarkDishAvailableUseCase markDishAvailableUseCase, ApplyPendingChangesUseCase applyPendingChangesUseCase, SchedulePendingChangesUseCase schedulePendingChangesUseCase, GetFoodMenuUseCase getFoodMenuUseCase) {
        this.createDishDraftUseCase = createDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.editDishUseCase = editDishUseCase;
        this.markDishOutOfStockUseCase = markDishOutOfStockUseCase;
        this.markDishAvailableUseCase = markDishAvailableUseCase;
        this.applyPendingChangesUseCase = applyPendingChangesUseCase;
        this.schedulePendingChangesUseCase = schedulePendingChangesUseCase;
        this.getFoodMenuUseCase = getFoodMenuUseCase;
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDto>> getDishes(@PathVariable String restaurantId) {
        FoodMenu foodMenu = getFoodMenuUseCase.getFoodMenu(UUID.fromString(restaurantId));

        List<DishDto> dishes = new java.util.ArrayList<>();
        for (Dish dish : foodMenu.getAllDishes()){
            DishVersionDto publishedDto = dish.getPublishedVersion()
                    .map(v -> new DishVersionDto(
                            v.name(),
                            v.description(),
                            v.price().asDouble(),
                            v.pictureUrl(),
                            v.tags(),
                            v.dishType().toString()
                    ))
                    .orElse(null);

            DishVersionDto draftDto = dish.getDraftVersion()
                    .map(v -> new DishVersionDto(
                            v.name(),
                            v.description(),
                            v.price().asDouble(),
                            v.pictureUrl(),
                            v.tags(),
                            v.dishType().toString()
                    ))
                    .orElse(null);

            dishes.add(new DishDto(
                            dish.getDishId().id(),
                            publishedDto,
                            draftDto,
                            dish.getState().name(),
                            dish.getScheduledPublishTime().orElse(null),
                            dish.getScheduledToBecomeState()
                    )
            );
        }

        return ResponseEntity.ok(dishes);
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
                DISH_TYPE.valueOf(request.dishType().toUpperCase())
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
                created.getState().name(),
                created.getScheduledPublishTime().orElse(null),
                created.getScheduledToBecomeState()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}/publish")
    public ResponseEntity<?> publishDish(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        DishStateChangeCommand command = new DishStateChangeCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                DishId.of(UUID.fromString(dishId))
        );

        try {
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

            DishDto responseDto = new DishDto(
                    published.getDishId().id(),
                    publishedDto,
                    null,
                    published.getState().name(),
                    published.getScheduledPublishTime().orElse(null),
                    published.getScheduledToBecomeState()
            );

            return ResponseEntity.ok(responseDto);

        } catch (IllegalStateException e) {
            // Handle the "more than 10 dishes" rule
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Cannot publish more than 10 dishes."));
        } catch (IllegalArgumentException e) {
            // Dish or menu not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Fallback for unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong: " + e.getMessage()));
        }
    }


    @PatchMapping("/{restaurantId}/dishes/{dishId}/unpublish")
    public ResponseEntity<DishDto> unpublishDish(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        DishStateChangeCommand command = new DishStateChangeCommand(
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
                unpublished.getState().name(),
                unpublished.getScheduledPublishTime().orElse(null),
                unpublished.getScheduledToBecomeState()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}/out-of-stock")
    public ResponseEntity<DishDto> markDishOutOfStock(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        DishStateChangeCommand command = new DishStateChangeCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                DishId.of(UUID.fromString(dishId))
        );

        Dish outOfStockDish = markDishOutOfStockUseCase.markDishOutOfStock(command);
        DishVersionDto publishedDto = outOfStockDish.getPublishedVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        DishVersionDto draftDto = outOfStockDish.getDraftVersion()
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
                outOfStockDish.getDishId().id(),
                publishedDto,
                draftDto,
                outOfStockDish.getState().name(),
                outOfStockDish.getScheduledPublishTime().orElse(null),
                outOfStockDish.getScheduledToBecomeState()
        ));
    }

    @PatchMapping("/{restaurantId}/dishes/{dishId}/available")
    public ResponseEntity<DishDto> markDishAvailable(
            @PathVariable String restaurantId,
            @PathVariable String dishId) {

        DishStateChangeCommand command = new DishStateChangeCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                DishId.of(UUID.fromString(dishId))
        );

        Dish availableDish = markDishAvailableUseCase.markDishAvailable(command);
        DishVersionDto publishedDto = availableDish.getPublishedVersion()
                .map(v -> new DishVersionDto(
                        v.name(),
                        v.description(),
                        v.price().asDouble(),
                        v.pictureUrl(),
                        v.tags(),
                        v.dishType().toString()
                ))
                .orElse(null);

        DishVersionDto draftDto = availableDish.getDraftVersion()
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
                availableDish.getDishId().id(),
                publishedDto,
                draftDto,
                availableDish.getState().name(),
                availableDish.getScheduledPublishTime().orElse(null),
                availableDish.getScheduledToBecomeState()
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
                editedDish.getState().name(),
                editedDish.getScheduledPublishTime().orElse(null),
                editedDish.getScheduledToBecomeState()
        ));
    }

    // what should be returned here
    @PostMapping("/{restaurantId}/dishes/apply-pending")
    public ResponseEntity<List<DishDto>> applyPendingDishChanges(
            @PathVariable String restaurantId) {

        // do I need to make a command here or can I just use restaurantId since that's the only attribute
        FoodMenu foodMenu = applyPendingChangesUseCase.applyPendingChanges(RestaurantId.of(UUID.fromString(restaurantId)));

        List<DishDto> publishedDishes = new java.util.ArrayList<>();
        for (Dish dish : foodMenu.getAllDishes()){
            DishVersionDto publishedDto = dish.getPublishedVersion()
                    .map(v -> new DishVersionDto(
                            v.name(),
                            v.description(),
                            v.price().asDouble(),
                            v.pictureUrl(),
                            v.tags(),
                            v.dishType().toString()
                    ))
                    .orElse(null);

            publishedDishes.add(new DishDto(
                    dish.getDishId().id(),
                    publishedDto,
                    null,
                    dish.getState().name(),
                    dish.getScheduledPublishTime().orElse(null),
                    dish.getScheduledToBecomeState()
                )
            );
        }
        return ResponseEntity.ok(publishedDishes);
    }

    @PatchMapping("/dishes/schedule")
    public ResponseEntity<List<DishDto>> schedulePublications(
            @RequestBody SchedulePublicationRequest request
    ){

        List<DishId> dishIds = request.dishIds().stream()
                .map(UUID::fromString)
                .map(DishId::of)
                .toList();
        ScheduleChangesCommand command = new ScheduleChangesCommand(
                RestaurantId.of(UUID.fromString(request.restaurantId())),
                dishIds,
                request.publicationTime(),
                request.stateToBecome()
        );

        FoodMenu foodMenu = schedulePendingChangesUseCase.scheduleChanges(command);

        List<DishDto> publishedDishes = new java.util.ArrayList<>();
        for (Dish dish : foodMenu.getAllDishes()){
            DishVersionDto publishedDto = dish.getPublishedVersion()
                    .map(v -> new DishVersionDto(
                            v.name(),
                            v.description(),
                            v.price().asDouble(),
                            v.pictureUrl(),
                            v.tags(),
                            v.dishType().toString()
                    ))
                    .orElse(null);

            publishedDishes.add(new DishDto(
                            dish.getDishId().id(),
                            publishedDto,
                            null,
                            dish.getState().name(),
                            dish.getScheduledPublishTime().orElse(null),
                            dish.getScheduledToBecomeState()
                    )
            );
        }

        return ResponseEntity.ok(publishedDishes);
    }
}
