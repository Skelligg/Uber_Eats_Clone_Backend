package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.common.events.dish.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Aggregate root representing the restaurant's published and draft menu.
 */
public class FoodMenu {
    private final RestaurantId restaurantId;
    private final List<Dish> dishes;
    private Price averageMenuPrice;

    private static final int MAX_PUBLISHED_DISHES = 10;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public FoodMenu(RestaurantId restaurantId) {
        this.restaurantId = restaurantId;
        this.dishes = new ArrayList<>();
        this.averageMenuPrice = Price.of(0.0);
    }


    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
    }

    public void updateDish(Dish updatedDish) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getDishId().equals(updatedDish.getDishId())) {
                dishes.set(i, updatedDish);
                recalculateAveragePrice();
                return;
            }
        }
        throw new NoSuchElementException("Dish not found in menu");
    }

    public List<Dish> getAllDishes() {
        return Collections.unmodifiableList(dishes);
    }

    public List<Dish> getPublishedDishes() {
        return dishes.stream()
                .filter(this::isPublished)
                .collect(Collectors.toList());
    }

    public List<Dish> applyPendingDrafts() {
        List<Dish> appliedDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.getDraftVersion().isPresent()) {
                if(countPublishedDishes() == MAX_PUBLISHED_DISHES){
                    throw new IllegalStateException("Cannot publish more than " + MAX_PUBLISHED_DISHES + " dishes at a time");
                }
                else {
                    dish.publish();
                    dish.addDomainEvent(new DishPublishedToMenuEvent(
                                    dish.getDishId().id(),
                                    restaurantId.id(),
                                    dish.getPublishedVersion().orElseThrow().name(),
                                    dish.getPublishedVersion().orElseThrow().description(),
                                    dish.getPublishedVersion().orElseThrow().price().amount(),
                                    dish.getPublishedVersion().orElseThrow().pictureUrl(),
                                    dish.getPublishedVersion().orElseThrow().tags(),
                                    dish.getPublishedVersion().orElseThrow().dishType().toString(),
                                    dish.getState().toString()
                            )
                    );
                    appliedDishes.add(dish);
                }
            }
        }
        recalculateAveragePrice();
        return appliedDishes;
    }


    public Price getAverageMenuPrice() {
        return averageMenuPrice;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    private int countPublishedDishes() {
        return (int) dishes.stream()
                .filter(this::isPublished)
                .count();
    }

    private void recalculateAveragePrice() {
        var published = getPublishedDishes();
        if (published.isEmpty()) {
            this.averageMenuPrice = Price.of(0.0);
        } else {
            double avg = published.stream()
                    .mapToDouble(d -> d.getPublishedVersion().orElseThrow().price().asDouble())
                    .average()
                    .orElse(0.0);
            this.averageMenuPrice = Price.of(avg);
        }
    }

    private boolean isPublished(Dish d) {
        return d.getState() == DISH_STATE.PUBLISHED || d.getState() == DISH_STATE.OUT_OF_STOCK;
    }

    private boolean isNotPublished(Dish d) {
        return d.getState() == DISH_STATE.DRAFT;
    }

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public List<Dish> scheduleDishes(List<DishId> dishIds, LocalDateTime publicationTime, DISH_STATE stateToBecome) {
        List<Dish> scheduled = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dishIds.contains(dish.getDishId())) {
                dish.schedule(publicationTime, stateToBecome);
                scheduled.add(dish);
            }
        }
        if (stateToBecome == DISH_STATE.PUBLISHED && (getPublishedDishes().size() + scheduled.size()) > MAX_PUBLISHED_DISHES){
            throw new IllegalStateException("Cannot schedule more than " + MAX_PUBLISHED_DISHES + " dishes at a time");
        }
        return scheduled;
    }

}
