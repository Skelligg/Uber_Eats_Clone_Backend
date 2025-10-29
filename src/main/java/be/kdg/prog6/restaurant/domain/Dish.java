package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Dish {
    private final DishId dishId;
    private DishVersion publishedVersion;
    private DishVersion draftVersion;
    private DISH_STATE state;
    private LocalDateTime scheduledPublishTime;
    private DISH_STATE scheduledToBecomeState;

    private final List<DomainEvent> domainEvents= new ArrayList<>();

    public Dish(DishVersion initialVersion) {
        this.dishId = DishId.newId();
        this.draftVersion = initialVersion;
        this.state = DISH_STATE.DRAFT;
    }

    public Dish(DishId dishId, DishVersion publishedVersion, DishVersion draftVersion, DISH_STATE state, LocalDateTime scheduledPublishTime, DISH_STATE scheduledToBecomeState) {
        this.dishId = dishId;
        this.publishedVersion = publishedVersion;
        this.draftVersion = draftVersion;
        this.state = state;
        this.scheduledPublishTime = scheduledPublishTime;
        this.scheduledToBecomeState = scheduledToBecomeState;
    }

    public void editDraft(DishVersion newDraft) {
        if (newDraft == null) throw new IllegalArgumentException("newDraft must not be null");
        this.draftVersion = newDraft;
    }

    public void publish() {
        if (draftVersion == null)
            throw new IllegalStateException("No draft to publish");

        this.publishedVersion = draftVersion;
        this.draftVersion = null;
        this.state = DISH_STATE.PUBLISHED;
        this.scheduledPublishTime = null;
        this.scheduledToBecomeState = null;
    }

    public void markAvailable() { this.state = DISH_STATE.PUBLISHED;}

    public void markOutOfStock() { this.state = DISH_STATE.OUT_OF_STOCK;}


    public void schedule(LocalDateTime time, DISH_STATE newState) {
        if (draftVersion == null && newState == DISH_STATE.PUBLISHED)
            throw new IllegalStateException("No draft to schedule");
        this.scheduledPublishTime = time;
        this.scheduledToBecomeState = newState;
    }

    public void unpublish() {
        this.state = DISH_STATE.DRAFT;
        this.draftVersion = publishedVersion;
        this.publishedVersion = null;
        this.scheduledPublishTime = null;
        this.scheduledToBecomeState = null;
    }

    public boolean isPublished() { return state == DISH_STATE.PUBLISHED || state == DISH_STATE.OUT_OF_STOCK;}

    // --- Accessors ---
    public DishId getDishId() {
        return dishId;
    }

    public Optional<DishVersion> getPublishedVersion() {
        return Optional.ofNullable(publishedVersion);
    }

    public Optional<DishVersion> getDraftVersion() {
        return Optional.ofNullable(draftVersion);
    }

    public DISH_STATE getState() {
        return state;
    }

    public Optional<LocalDateTime> getScheduledPublishTime() {
        return Optional.ofNullable(scheduledPublishTime);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish other = (Dish) o;
        return dishId.equals(other.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishId);
    }

    public boolean isToBecomePublished() { return scheduledToBecomeState == DISH_STATE.PUBLISHED;}

    public DISH_STATE getScheduledToBecomeState() {
        return scheduledToBecomeState;
    }
}
