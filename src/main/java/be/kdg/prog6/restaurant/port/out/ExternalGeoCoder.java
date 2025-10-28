package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.Coordinates;

import java.util.Optional;

public interface ExternalGeoCoder {
    Optional<Coordinates> fetchCoordinates(Address address);
}
