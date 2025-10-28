package be.kdg.prog6.restaurant.core.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.port.in.order.GetOrderProjectionsUseCase;
import be.kdg.prog6.restaurant.port.out.order.LoadOrdersPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrderProjectionsUseCaseImpl implements GetOrderProjectionsUseCase {
    private final LoadOrdersPort loadOrdersPort;

    public GetOrderProjectionsUseCaseImpl(LoadOrdersPort loadOrdersPort) {
        this.loadOrdersPort = loadOrdersPort;
    }


    @Override
    public List<OrderProjection> getOrders(be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId restaurantId) {
        List<OrderProjection> allOrders = loadOrdersPort.loadAllByRestaurantId(restaurantId);

        return allOrders;
    }
}
