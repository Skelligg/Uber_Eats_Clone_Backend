package be.kdg.prog6.ordering.core.dishProjection;

import be.kdg.prog6.ordering.adaptor.in.request.FilterDishesRequest;
import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.in.dishProjection.GetDishesUseCase;
import be.kdg.prog6.ordering.port.out.dishProjection.LoadDishesPort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GetDishesUseCaseImpl implements GetDishesUseCase {
    private final LoadDishesPort loadDishesPort;

    public GetDishesUseCaseImpl(LoadDishesPort loadDishesPort) {
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    public List<DishProjection> getDishes(FilterDishesRequest request) {
        List<DishProjection> all = loadDishesPort.loadAllByRestaurantId(request.restaurantId());

        return all.stream()
                .filter(d -> request.dishType().isEmpty()
                        || d.getDishType().toString().equalsIgnoreCase(request.dishType()))

                // Filter by tags (comma-separated in both request and entity)
                .filter(d -> {
                    if (request.tags().isEmpty()) {
                        return true;
                    }
                    List<String> requestedTags = Arrays.stream(request.tags().split(","))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .toList();

                    List<String> dishTags = Arrays.stream(d.getTags().split(","))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .toList();

                    // must contain *all* requested tags
                    return dishTags.containsAll(requestedTags);
                })
                .toList();
    }
}
