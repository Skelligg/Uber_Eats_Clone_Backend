package be.kdg.prog6.restaurant.adaptor.in.controller;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.restaurant.adaptor.in.request.CreateRestaurantRequest;
import be.kdg.prog6.restaurant.adaptor.in.request.OrderRejectedRequest;
import be.kdg.prog6.restaurant.adaptor.in.response.OrderDto;
import be.kdg.prog6.restaurant.adaptor.in.response.OrderlineDto;
import be.kdg.prog6.restaurant.adaptor.in.response.RestaurantDto;
import be.kdg.prog6.restaurant.core.restaurant.DefaultCreateRestaurantUseCase;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;
import be.kdg.prog6.restaurant.port.in.orderProjection.HandleOrderUseCase;
import be.kdg.prog6.restaurant.port.in.orderProjection.GetOrderProjectionsUseCase;
import be.kdg.prog6.restaurant.port.in.orderProjection.MarkOrderReadyForPickUpUseCase;
import be.kdg.prog6.restaurant.port.in.restaurant.CreateRestaurantCommand;
import be.kdg.prog6.restaurant.port.in.restaurant.GetRestaurantUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final DefaultCreateRestaurantUseCase createRestaurantUseCase;
    private final GetOrderProjectionsUseCase getOrderProjectionsUseCase;
    private final HandleOrderUseCase handleOrderUseCase;
    private final MarkOrderReadyForPickUpUseCase markOrderReadyForPickUp ;
    private final GetRestaurantUseCase getRestaurantUseCase;

    public RestaurantController(
            DefaultCreateRestaurantUseCase createRestaurantUseCase, GetOrderProjectionsUseCase getOrderProjectionsUseCase, HandleOrderUseCase handleOrderUseCase, MarkOrderReadyForPickUpUseCase markOrderReadyForPickUp, GetRestaurantUseCase getRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getOrderProjectionsUseCase = getOrderProjectionsUseCase;
        this.handleOrderUseCase = handleOrderUseCase;
        this.markOrderReadyForPickUp = markOrderReadyForPickUp;
        this.getRestaurantUseCase = getRestaurantUseCase;
    }

    @GetMapping
    public ResponseEntity<RestaurantDto> getRestaurant(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getClaimAsString("sub");
        UUID ownerId = UUID.fromString(subject);

        try {
            Restaurant restaurant = getRestaurantUseCase.getRestaurant(ownerId);

            RestaurantDto dto = new RestaurantDto(
                    restaurant.getRestaurantId().id().toString(),
                    restaurant.getName(),
                    restaurant.getAddress().street(),
                    restaurant.getAddress().number(),
                    restaurant.getAddress().postalCode(),
                    restaurant.getAddress().city(),
                    restaurant.getAddress().country(),
                    restaurant.getEmailAddress().emailAddress(),
                    restaurant.getPictureList().stream().map(Picture::url).toList(),
                    restaurant.getCuisineType().name(),
                    restaurant.getDefaultPrepTime().minTime(),
                    restaurant.getDefaultPrepTime().maxTime(),
                    restaurant.getOpeningHours().openingTime().toString(),
                    restaurant.getOpeningHours().closingTime().toString(),
                    restaurant.getOpeningHours().openDays().stream().map(Enum::name).toList()
            );

            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            // restaurant not found
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody CreateRestaurantRequest request, @AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getClaimAsString("sub"));
        String ownerName = jwt.getClaimAsString("name");

        CreateRestaurantCommand command = new CreateRestaurantCommand(
                OwnerId.of(ownerId,ownerName),
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

        Restaurant restaurant = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(new RestaurantDto(
                restaurant.getRestaurantId().id().toString(),
                restaurant.getName(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getEmailAddress().emailAddress(),
                restaurant.getPictureList().stream().map(Picture::url).toList(),
                restaurant.getCuisineType().name(),
                restaurant.getDefaultPrepTime().minTime(),
                restaurant.getDefaultPrepTime().maxTime(),
                restaurant.getOpeningHours().openingTime().toString(),
                restaurant.getOpeningHours().closingTime().toString(),
                restaurant.getOpeningHours().openDays().stream().map(Enum::name).toList()
        ));
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String restaurantId, @AuthenticationPrincipal Jwt jwt) {

        List<OrderDto> orders = getOrderProjectionsUseCase.getOrders(
                        RestaurantId.of(UUID.fromString(restaurantId))
                ).stream()
                .map(o -> new OrderDto(
                        o.getOrderId(),
                        o.getAddress().street(),
                        o.getAddress().number(),
                        o.getAddress().postalCode(),
                        o.getAddress().city(),
                        o.getAddress().country(),
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

    @PatchMapping("/orders/{orderId}/accept")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OrderDto> acceptOrder(@PathVariable String orderId, @AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getClaimAsString("sub");
        UUID ownerId = UUID.fromString(subject);

        OrderProjection order = handleOrderUseCase.acceptOrder(UUID.fromString(orderId),ownerId);

        return ResponseEntity.ok(new OrderDto(
                order.getOrderId(),
                order.getAddress().street(),
                order.getAddress().number(),
                order.getAddress().postalCode(),
                order.getAddress().city(),
                order.getAddress().country(),
                order.getTotalPrice(),
                order.getPlacedAt(),
                order.getStatus(),
                order.getRejectionReason(),
                order.getAcceptedAt() != null ? order.getAcceptedAt(): null,
                order.getReadyAt() != null ? order.getReadyAt(): null,
                order.getRejectedAt() != null ? order.getRejectedAt(): null,
                order.getPickedUpAt() != null ? order.getPickedUpAt(): null,
                order.getDeliveredAt() != null ? order.getDeliveredAt(): null,
                order.getLines().stream()
                        .map(l -> new OrderlineDto(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice(),
                                l.linePrice()
                        ))
                        .toList()
        ));
    }

    @PatchMapping("/orders/{orderId}/reject")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OrderDto> rejectOrder(@PathVariable String orderId, @AuthenticationPrincipal Jwt jwt, @RequestBody OrderRejectedRequest request) {
        String subject = jwt.getClaimAsString("sub");
        UUID ownerId = UUID.fromString(subject);

        OrderProjection order = handleOrderUseCase.rejectOrder(UUID.fromString(orderId),ownerId, request.reason());

        return ResponseEntity.ok(new OrderDto(
                order.getOrderId(),
                order.getAddress().street(),
                order.getAddress().number(),
                order.getAddress().postalCode(),
                order.getAddress().city(),
                order.getAddress().country(),
                order.getTotalPrice(),
                order.getPlacedAt(),
                order.getStatus(),
                order.getRejectionReason(),
                order.getAcceptedAt() != null ? order.getAcceptedAt(): null,
                order.getReadyAt() != null ? order.getReadyAt(): null,
                order.getRejectedAt() != null ? order.getRejectedAt(): null,
                order.getPickedUpAt() != null ? order.getPickedUpAt(): null,
                order.getDeliveredAt() != null ? order.getDeliveredAt(): null,
                order.getLines().stream()
                        .map(l -> new OrderlineDto(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice(),
                                l.linePrice()
                        ))
                        .toList()
        ));
    }

    @PatchMapping("/orders/accept")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OrderDto> acceptLastOrder( @AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getClaimAsString("sub");
        UUID ownerId = UUID.fromString(subject);

        OrderProjection order = handleOrderUseCase.acceptLastOrder(ownerId);

        return ResponseEntity.ok(new OrderDto(
                order.getOrderId(),
                order.getAddress().street(),
                order.getAddress().number(),
                order.getAddress().postalCode(),
                order.getAddress().city(),
                order.getAddress().country(),
                order.getTotalPrice(),
                order.getPlacedAt(),
                order.getStatus(),
                order.getRejectionReason(),
                order.getAcceptedAt() != null ? order.getAcceptedAt(): null,
                order.getReadyAt() != null ? order.getReadyAt(): null,
                order.getRejectedAt() != null ? order.getRejectedAt(): null,
                order.getPickedUpAt() != null ? order.getPickedUpAt(): null,
                order.getDeliveredAt() != null ? order.getDeliveredAt(): null,
                order.getLines().stream()
                        .map(l -> new OrderlineDto(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice(),
                                l.linePrice()
                        ))
                        .toList()
        ));
    }



    @PatchMapping("/orders/{orderId}/readyforpickup")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OrderDto> markOrderReadyForPickUp(@PathVariable String orderId, @AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getClaimAsString("sub");
        UUID ownerId = UUID.fromString(subject);

        OrderProjection order = markOrderReadyForPickUp.markOrderReadyForPickUp(UUID.fromString(orderId),ownerId);

        return ResponseEntity.ok(new OrderDto(
                order.getOrderId(),
                order.getAddress().street(),
                order.getAddress().number(),
                order.getAddress().postalCode(),
                order.getAddress().city(),
                order.getAddress().country(),
                order.getTotalPrice(),
                order.getPlacedAt(),
                order.getStatus(),
                order.getRejectionReason(),
                order.getAcceptedAt() != null ? order.getAcceptedAt(): null,
                order.getReadyAt() != null ? order.getReadyAt(): null,
                order.getRejectedAt() != null ? order.getRejectedAt(): null,
                order.getPickedUpAt() != null ? order.getPickedUpAt(): null,
                order.getDeliveredAt() != null ? order.getDeliveredAt(): null,
                order.getLines().stream()
                        .map(l -> new OrderlineDto(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice(),
                                l.linePrice()
                        ))
                        .toList()
        ));
    }

}
