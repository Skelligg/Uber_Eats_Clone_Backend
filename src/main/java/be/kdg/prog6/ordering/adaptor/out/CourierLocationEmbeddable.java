package be.kdg.prog6.ordering.adaptor.out;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class CourierLocationEmbeddable {
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    protected CourierLocationEmbeddable() {}

    public CourierLocationEmbeddable(double latitude, double longitude, LocalDateTime timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
