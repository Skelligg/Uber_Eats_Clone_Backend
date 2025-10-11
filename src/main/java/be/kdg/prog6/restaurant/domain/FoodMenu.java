package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;

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

    /**
     * Add a new dish to the menu.
     * Enforces the invariant: no more than 10 published dishes.
     */
    public void addDish(Dish dish) {
        if (isPublished(dish) && countPublishedDishes() >= MAX_PUBLISHED_DISHES) {
            throw new IllegalStateException("Cannot publish more than " + MAX_PUBLISHED_DISHES + " dishes at a time");
        }

        dishes.add(dish);
        recalculateAveragePrice();
    }

    /**
     * Update a dish (e.g. after publishing or editing a draft).
     */
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

    /**
     * Returns all dishes (drafts + published + unpublished).
     */
    public List<Dish> getAllDishes() {
        return Collections.unmodifiableList(dishes);
    }

    /**
     * Returns only published dishes.
     */
    public List<Dish> getPublishedDishes() {
        return dishes.stream()
                .filter(this::isPublished)
                .collect(Collectors.toList());
    }

    /**
     * Average price of published dishes.
     */
    public Price getAverageMenuPrice() {
        return averageMenuPrice;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    // --- Internal helpers ---
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

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
