package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.common.events.dish.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.dish.DishUnpublishedToMenuEvent;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DishPublicationScheduler {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final UpdateDishPort updateDishPort;

    public DishPublicationScheduler(LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts, UpdateDishPort updateDishPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
        this.updateDishPort = updateDishPort;
    }

    @Scheduled(fixedRate = 60000) // every minute
    @Transactional
    public void publishScheduledDishes() {
        // Load all food menus
        List<FoodMenu> allMenus = loadFoodMenuPort.loadAll();

        LocalDateTime now = LocalDateTime.now();

        allMenus.forEach(menu -> {
            var dishesScheduled = menu.getAllDishes().stream()
                    .filter(d -> d.getScheduledPublishTime().isPresent())
                    .filter(d -> d.getScheduledPublishTime().get().isBefore(now) ||
                            d.getScheduledPublishTime().get().isEqual(now))
                    .toList();

            if (!dishesScheduled.isEmpty()) {
                dishesScheduled.forEach(d -> {
                    if(!d.isToBecomePublished()){
                        d.unpublish();
                        menu.updateDish(d);
                        d.addDomainEvent(new DishUnpublishedToMenuEvent(
                                d.getDishId().id()
                        ) );
                    }
                    else{
                        menu.publishDish(d);
                        menu.addDish(d);
                        d.addDomainEvent(new DishPublishedToMenuEvent(
                                        d.getDishId().id(),
                                        menu.getRestaurantId().id(),
                                        d.getPublishedVersion().orElseThrow().name(),
                                        d.getPublishedVersion().orElseThrow().description(),
                                        d.getPublishedVersion().orElseThrow().price().amount(),
                                        d.getPublishedVersion().orElseThrow().pictureUrl(),
                                        d.getPublishedVersion().orElseThrow().tags(),
                                        d.getPublishedVersion().orElseThrow().dishType().toString(),
                                        d.getState().toString()
                                )
                        );
                    }
                    updateDishPort.updateDish(d);
                });
                this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(menu));
            }
        });
    }
}
