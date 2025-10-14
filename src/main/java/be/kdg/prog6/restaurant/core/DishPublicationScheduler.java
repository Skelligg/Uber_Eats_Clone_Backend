package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DishPublicationScheduler {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public DishPublicationScheduler(LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
    }

    @Scheduled(fixedRate = 60000) // every minute
    @Transactional
    public void publishScheduledDishes() {
        // Load all food menus (you might want to make a port that fetches all)
        List<FoodMenu> allMenus = loadFoodMenuPort.loadAll();

        LocalDateTime now = LocalDateTime.now();

        allMenus.forEach(menu -> {
            var dishesToPublish = menu.getPublishedDishes().stream()
                    .filter(d -> d.getScheduledPublishTime().isPresent())
                    .filter(d -> d.getScheduledPublishTime().get().isBefore(now) ||
                            d.getScheduledPublishTime().get().isEqual(now))
                    .toList();

            if (!dishesToPublish.isEmpty()) {
                dishesToPublish.forEach(d -> {
                    d.publish();
                    menu.updateDish(d);
                });
                updateFoodMenuPort.updateFoodMenu(menu);
            }
        });
    }
}
