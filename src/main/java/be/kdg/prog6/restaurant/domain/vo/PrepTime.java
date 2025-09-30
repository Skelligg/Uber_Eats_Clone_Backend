package be.kdg.prog6.restaurant.domain.vo;

public record PrepTime(
        int minTime,
        int maxTime
) {
    public PrepTime {
        if (minTime > maxTime) {
            throw new IllegalArgumentException("Min. Time cannot be greater than Max. Time");
        }
        if (minTime <= 0) {
            throw new IllegalArgumentException("Min Time cannot be less than 0");
        }
    }
}
