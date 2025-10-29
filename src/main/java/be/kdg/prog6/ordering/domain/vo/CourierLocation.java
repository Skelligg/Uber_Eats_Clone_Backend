package be.kdg.prog6.ordering.domain.vo;

import java.time.LocalDateTime;

public record  CourierLocation(double lat, double lng, LocalDateTime when) { }
