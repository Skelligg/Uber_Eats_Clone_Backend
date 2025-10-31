package be.kdg.prog6.ordering.core.order;

import be.kdg.prog6.ordering.domain.vo.CourierLocation;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.port.in.order.*;

import be.kdg.prog6.ordering.port.out.order.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.order.UpdateOrderPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderProjectionEventProjectorImpl implements OrderProjectionEventProjector {

    private final LoadOrderPort loadOrdersPort;
    private final List<UpdateOrderPort> updateOrderPorts;

    public OrderProjectionEventProjectorImpl(LoadOrderPort loadOrdersPort, List<UpdateOrderPort> updateOrderPorts) {
        this.loadOrdersPort = loadOrdersPort;
        this.updateOrderPorts = updateOrderPorts;
    }

    @Override
    public void project(OrderAcceptedCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        order.get().accepted();
        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }

    @Override
    public void project(OrderReadyForPickupCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        order.get().markReadyForPickup();
        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }

    @Override
    public void project(OrderPickedUpCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        order.get().pickedUp();
        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }

    @Override
    public void project(OrderDeliveredCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            return;
            //throw new IllegalArgumentException("Order does not exist");
        }
        order.get().markDelivered();

        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }

    @Override
    public void project(OrderLocationUpdatedCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            return;
//            throw new IllegalArgumentException("Order does not exist");
            // kept being spammed in my log, possible issue with delivery service?
        }
        order.get().pickedUp();
        order.get().updateCourierLocation(new CourierLocation(command.lat(),command.lng(),command.occurredAt()));

        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }

    @Override
    public void project(OrderRejectedCommand command) {
        var order = loadOrdersPort.findById(OrderId.of(command.orderId()));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        order.get().rejected(command.reason());
        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }
}
