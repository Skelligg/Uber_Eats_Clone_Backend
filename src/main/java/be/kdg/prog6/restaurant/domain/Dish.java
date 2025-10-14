package be.kdg.prog6.restaurant.domain;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Dish aggregate that keeps track of a published version and an optional draft version.
 */
public class Dish {
    private final DishId dishId;
    private DishVersion publishedVersion;
    private DishVersion draftVersion;
    private DISH_STATE state;
    private LocalDateTime scheduledPublishTime;

    private final List<DomainEvent> domainEvents= new ArrayList<>();

    public Dish(DishVersion initialVersion) {
        this.dishId = DishId.newId();
        this.draftVersion = initialVersion;
        this.state = DISH_STATE.UNPUBLISHED;
    }

    public Dish(DishId dishId, DishVersion publishedVersion, DishVersion draftVersion, DISH_STATE state, LocalDateTime scheduledPublishTime) {
        this.dishId = dishId;
        this.publishedVersion = publishedVersion;
        this.draftVersion = draftVersion;
        this.state = state;
        this.scheduledPublishTime = scheduledPublishTime;
    }

    /**
     * Convenience: set the whole draft from a DishVersion instance.
     */
    public void editDraft(DishVersion newDraft) {
        if (newDraft == null) throw new IllegalArgumentException("newDraft must not be null");
        this.draftVersion = newDraft;
    }

    // --- Publish the draft immediately ---
    public void publish() {
        if (draftVersion == null)
            throw new IllegalStateException("No draft to publish");

        this.publishedVersion = draftVersion;
        this.draftVersion = null;
        this.state = DISH_STATE.PUBLISHED;
        this.scheduledPublishTime = null;
    }

    public void markAvailable() { this.state = DISH_STATE.PUBLISHED;}

    public void markOutOfStock() { this.state = DISH_STATE.OUT_OF_STOCK;}


    public void schedulePublication(LocalDateTime time) {
        if (draftVersion == null)
            throw new IllegalStateException("No draft to schedule");
        this.scheduledPublishTime = time;
    }

    // --- Unpublish a dish entirely ---
    public void unpublish() {
        this.state = DISH_STATE.UNPUBLISHED;
        this.draftVersion = publishedVersion;
        this.publishedVersion = null;
        this.scheduledPublishTime = null;
    }

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

    public void setPublishedVersion(DishVersion publishedVersion) {
        this.publishedVersion = publishedVersion;
    }

    public void setDraftVersion(DishVersion draftVersion) {
        this.draftVersion = draftVersion;
    }

    public void setState(DISH_STATE state) {
        this.state = state;
    }

    public void setScheduledPublishTime(LocalDateTime scheduledPublishTime) {
        this.scheduledPublishTime = scheduledPublishTime;
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

}
