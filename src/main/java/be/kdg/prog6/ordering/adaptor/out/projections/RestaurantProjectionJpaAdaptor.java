package be.kdg.prog6.ordering.adaptor.out.projections;

import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.common.vo.DAY;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.out.restaurantProjection.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.restaurantProjection.UpdateRestaurantsPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestaurantProjectionJpaAdaptor implements LoadRestaurantsPort, UpdateRestaurantsPort {

    private final RestaurantProjectionJpaRepository restaurantProjectionJpaRepository;

    public RestaurantProjectionJpaAdaptor(RestaurantProjectionJpaRepository restaurantProjectionJpaRepository) {
        this.restaurantProjectionJpaRepository = restaurantProjectionJpaRepository;
    }

    @Override
    public List<RestaurantProjection> loadAll() {
        List<RestaurantProjectionJpaEntity> restaurantProjections = this.restaurantProjectionJpaRepository.findAll();
        return restaurantProjections.stream()
                .map(entity -> new RestaurantProjection(
                        entity.getId(),
                        entity.getName(),
                        entity.getStreet(),
                        entity.getNumber(),
                        entity.getPostalCode(),
                        entity.getCity(),
                        entity.getCountry(),
                        entity.getEmailAddress(),
                        entity.getPictures(),
                        CUISINE_TYPE.valueOf(entity.getCuisineType()),
                        entity.getMinPrepTime(),
                        entity.getMaxPrepTime(),
                        entity.getOpeningTime(),
                        entity.getClosingTime(),
                        entity.getOpenDays().stream().map(DAY::valueOf).collect(Collectors.toList())
                ))
                .toList();
    }

    @Override
    public Optional<RestaurantProjection> findById(UUID restaurantId) {
        return restaurantProjectionJpaRepository.findById(restaurantId)
                .map(this::toDomain);
    }

    private RestaurantProjection toDomain(RestaurantProjectionJpaEntity entity) {
        return new RestaurantProjection(
                entity.getId(),
                entity.getName(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getCountry(),
                entity.getEmailAddress(),
                entity.getPictures().stream()
                        .map(String::new)
                        .collect(Collectors.toList()),
                CUISINE_TYPE.valueOf(entity.getCuisineType()),
                entity.getMinPrepTime(),
                entity.getMaxPrepTime(),
                entity.getOpeningTime(),
                entity.getClosingTime(),
                entity.getOpenDays().stream().map(DAY::valueOf).collect(Collectors.toList())
        );
    }

    @Override
    public RestaurantProjection update(RestaurantProjection restaurantProjection) {
        RestaurantProjectionJpaEntity entity = new RestaurantProjectionJpaEntity(
                restaurantProjection.getRestaurantId(),
                restaurantProjection.getName(),
                restaurantProjection.getStreet(),
                restaurantProjection.getNumber(),
                restaurantProjection.getPostalCode(),
                restaurantProjection.getCity(),
                restaurantProjection.getCountry(),
                restaurantProjection.getEmailAddress(),
                restaurantProjection.getPictures(),
                restaurantProjection.getCuisineType().toString(),
                restaurantProjection.getMinPrepTime(),
                restaurantProjection.getMaxPrepTime(),
                restaurantProjection.getOpeningTime(),
                restaurantProjection.getClosingTime(),
                restaurantProjection.getOpenDays().stream().map(Enum::name).toList()
        );

        RestaurantProjectionJpaEntity saved = restaurantProjectionJpaRepository.save(entity);

        return new RestaurantProjection(
                saved.getId(),
                saved.getName(),
                saved.getStreet(),
                saved.getNumber(),
                saved.getPostalCode(),
                saved.getCity(),
                saved.getCountry(),
                saved.getEmailAddress(),
                saved.getPictures(),
                CUISINE_TYPE.valueOf(saved.getCuisineType()),
                saved.getMinPrepTime(),
                saved.getMaxPrepTime(),
                saved.getOpeningTime(),
                saved.getClosingTime(),
                saved.getOpenDays().stream().map(DAY::valueOf).collect(Collectors.toList())
        );
    }

}
