package be.kdg.prog6.restaurant.adaptor.in;

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
import be.kdg.prog6.restaurant.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    public FoodMenuController(CreateDishDraftUseCase createDishDraftUseCase, PublishDishUseCase publishDishUseCase, UnpublishDishUseCase unpublishDishUseCase, EditDishUseCase editDishUseCase, MarkDishOutOfStockUseCase markDishOutOfStockUseCase, MarkDishAvailableUseCase markDishAvailableUseCase, ApplyPendingChangesUseCase applyPendingChangesUseCase, SchedulePendingChangesUseCase schedulePendingChangesUseCase) {
        this.createDishDraftUseCase = createDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.editDishUseCase = editDishUseCase;
        this.markDishOutOfStockUseCase = markDishOutOfStockUseCase;
        this.markDishAvailableUseCase = markDishAvailableUseCase;
        this.applyPendingChangesUseCase = applyPendingChangesUseCase;
        this.schedulePendingChangesUseCase = schedulePendingChangesUseCase;
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

        DishStateChangeCommand command = new DishStateChangeCommand(
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
                unpublished.getState().name()
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
                outOfStockDish.getState().name()
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
                availableDish.getState().name()
        ));
    }
    // ask about restful api conventions, am I allowed to have actions at the end of apis, more DDD to do so than
    // following restful principles

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
                    dish.getState().name()
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

        return ResponseEntity.ok().build();
    }
}
