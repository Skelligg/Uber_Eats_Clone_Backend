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

    public Dish(DishId dishId, DishVersion initialVersion) {
        this.dishId = dishId;
        this.publishedVersion = initialVersion;
        this.state = DISH_STATE.PUBLISHED;
    }

    /**
     * Edit (create or update) a draft version using individual fields.
     * If there is an existing draft, update it; otherwise copy from publishedVersion if it exists,
     * or create a fresh DishVersion when there is no published version.
     */
    public void editDraft(String name,
                          String description,
                          Price price,
                          String pictureUrl,
                          String tags,
                          DISH_TYPE dishType) {
        this.draftVersion = (this.draftVersion == null)
                ? (publishedVersion != null
                ? publishedVersion.update(name, description, price, pictureUrl, tags, dishType)
                : new DishVersion(name, description, price, pictureUrl, tags, dishType))
                : draftVersion.update(name, description, price, pictureUrl, tags, dishType);
    }

    /**
     * Convenience: set the whole draft from a DishVersion instance.
     */
    public void editDraft(DishVersion newDraft) {
        if (newDraft == null) throw new IllegalArgumentException("newDraft must not be null");
        this.draftVersion = newDraft;
    }

    // --- Publish the draft immediately ---
    public void publishNow() {
        if (draftVersion == null)
            throw new IllegalStateException("No draft to publish");

        this.publishedVersion = draftVersion;
        this.draftVersion = null;
        this.state = DISH_STATE.PUBLISHED;
        this.scheduledPublishTime = null;
    }

    // --- Schedule publication ---
    public void schedulePublication(LocalDateTime time) {
        if (draftVersion == null)
            throw new IllegalStateException("No draft to schedule");
        this.scheduledPublishTime = time;
    }

    // --- Unpublish a dish entirely ---
    public void unpublish() {
        this.state = DISH_STATE.UNPUBLISHED; //ask chat about the 4 states and if everything adds up.
        this.draftVersion = null;
        this.scheduledPublishTime = null;
        // keep publishedVersion? a decision: here we keep it but mark state UNPUBLISHED.
        // If you want to remove published data, set publishedVersion = null;
    }

    // --- Accessors ---
    public DishId getDishId() {
        return dishId;
    }

    public DishVersion getPublishedVersion() {
        return publishedVersion;
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

    protected void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

}
