package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.OrderDto;
import be.kdg.prog6.restaurant.adaptor.in.response.OrderlineDto;
import be.kdg.prog6.restaurant.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.restaurant.core.restaurant.DefaultCreateRestaurantUseCase;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;
import be.kdg.prog6.restaurant.port.in.order.GetOrdersUseCase;
import be.kdg.prog6.restaurant.port.in.restaurant.CreateRestaurantCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// have to secure these controllers now.. do preauthorize('owner'), then have to
// either find restaurant/foodmenu with owner id first or check owner id with restaurants ownerid.
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final DefaultCreateRestaurantUseCase createRestaurantUseCase;
    private final GetOrdersUseCase getOrdersUseCase;

    public RestaurantController(
            DefaultCreateRestaurantUseCase createRestaurantUseCase, GetOrdersUseCase getOrdersUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        CreateRestaurantCommand command = new CreateRestaurantCommand(
                OwnerId.of(request.ownerId()),
                request.name(),
                new Address(
                        request.address().street(),
                        request.address().number(),
                        request.address().postalCode(),
                        request.address().city(),
                        request.address().country()
                ),
                new EmailAddress(request.emailAddress()),
                request.pictures().stream()
                        .map(Picture::new)
                        .collect(Collectors.toList()),
                CUISINE_TYPE.valueOf(request.cuisineType().toUpperCase()),
                new PrepTime(request.minPrepTime(), request.maxPrepTime()),
                new OpeningHours(request.openingTime(), request.closingTime(), request.openDays())
        );

        Restaurant restaurantCreated = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(
                restaurantCreated.getRestaurantId().id().toString(),
                restaurantCreated.getName()
        ));
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String restaurantId) {

        List<OrderDto> orders = getOrdersUseCase.getOrders(
                        RestaurantId.of(UUID.fromString(restaurantId))
                ).stream()
                .map(o -> new OrderDto(
                        o.getOrderId(),
                        o.getStreet(),
                        o.getNumber(),
                        o.getPostalCode(),
                        o.getCity(),
                        o.getCountry(),
                        o.getTotalPrice(),
                        o.getPlacedAt(),
                        o.getStatus(),
                        o.getRejectionReason(),
                        o.getAcceptedAt() != null ? o.getAcceptedAt(): null,
                        o.getReadyAt() != null ? o.getReadyAt(): null,
                        o.getRejectedAt() != null ? o.getRejectedAt(): null,
                        o.getPickedUpAt() != null ? o.getPickedUpAt(): null,
                        o.getDeliveredAt() != null ? o.getDeliveredAt(): null,
                        o.getLines().stream()
                                .map(l -> new OrderlineDto(
                                        l.dishId().id(), // UUID
                                        l.dishName(),
                                        l.quantity(),
                                        l.unitPrice(),
                                        l.linePrice()
                                ))
                                .toList()
                ))
                .toList();


        return ResponseEntity.ok(orders);
    }

}
