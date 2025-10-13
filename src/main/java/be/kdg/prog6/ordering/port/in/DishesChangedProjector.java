package be.kdg.prog6.ordering.port.in;

public interface DishesChangedProjector {
    void project(DishPublishedCommand command);
    void project(DishUnpublishedCommand command);
    void project(DishMarkedOutOfStockCommand command);
    void project(DishMarkedAvailableCommand event);
}
