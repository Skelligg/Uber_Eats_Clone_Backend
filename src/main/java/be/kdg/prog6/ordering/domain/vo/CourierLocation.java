package be.kdg.prog6.ordering.domain.vo;

import java.time.Instant;
import java.time.LocalDateTime;

public record  CourierLocation(double lat, double lon, LocalDateTime when) { }
