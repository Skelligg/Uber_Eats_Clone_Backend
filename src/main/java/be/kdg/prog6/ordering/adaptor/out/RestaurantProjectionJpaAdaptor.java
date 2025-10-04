package be.kdg.prog6.ordering.adaptor.out;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.out.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.UpdateRestaurantsPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantProjectionJpaAdaptor implements LoadRestaurantsPort, UpdateRestaurantsPort {

    private final RestaurantsJpaRepository restaurantsJpaRepository;

    public RestaurantProjectionJpaAdaptor(RestaurantsJpaRepository restaurantsJpaRepository) {
        this.restaurantsJpaRepository = restaurantsJpaRepository;
    }

    @Override
    public List<RestaurantProjection> loadAll() {
        List<RestaurantProjectionJpaEntity> restaurantProjections = this.restaurantsJpaRepository.findAll();
        return restaurantProjections.stream()
                .map(entity -> new RestaurantProjection(
                        entity.getId(),
                        entity.getOwnerId(),
                        entity.getName(),
                        entity.getStreet(),
                        entity.getNumber(),
                        entity.getPostalCode(),
                        entity.getCity(),
                        entity.getCountry(),
                        entity.getEmailAddress(),
                        entity.getPictures(),
                        entity.getCuisineType(),
                        entity.getMinPrepTime(),
                        entity.getMaxPrepTime(),
                        entity.getOpeningTime(),
                        entity.getClosingTime(),
                        entity.getOpenDays()
                ))
                .toList();
    }

    @Override
    public RestaurantProjection update(RestaurantProjection restaurantProjection) {
        RestaurantProjectionJpaEntity entity = new RestaurantProjectionJpaEntity(
                restaurantProjection.getRestaurantId(),
                restaurantProjection.getOwnerId(),
                restaurantProjection.getName(),
                restaurantProjection.getStreet(),
                restaurantProjection.getNumber(),
                restaurantProjection.getPostalCode(),
                restaurantProjection.getCity(),
                restaurantProjection.getCountry(),
                restaurantProjection.getEmailAddress(),
                restaurantProjection.getPictures(),
                restaurantProjection.getCuisineType(),
                restaurantProjection.getMinPrepTime(),
                restaurantProjection.getMaxPrepTime(),
                restaurantProjection.getOpeningTime(),
                restaurantProjection.getClosingTime(),
                restaurantProjection.getOpenDays()
        );

        RestaurantProjectionJpaEntity saved = restaurantsJpaRepository.save(entity);

        return new RestaurantProjection(
                saved.getId(),
                saved.getOwnerId(),
                saved.getName(),
                saved.getStreet(),
                saved.getNumber(),
                saved.getPostalCode(),
                saved.getCity(),
                saved.getCountry(),
                saved.getEmailAddress(),
                saved.getPictures(),
                saved.getCuisineType(),
                saved.getMinPrepTime(),
                saved.getMaxPrepTime(),
                saved.getOpeningTime(),
                saved.getClosingTime(),
                saved.getOpenDays()
        );
    }

}
