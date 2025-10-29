package be.kdg.prog6.restaurant.adaptor.out.opencage;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.Coordinates;
import be.kdg.prog6.restaurant.port.out.geocoder.ExternalGeoCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class OpenCageGeoCoderImpl implements ExternalGeoCoder {

    private static final Logger log = LoggerFactory.getLogger(OpenCageGeoCoderImpl.class);
    private static final String OPENCAGE_URL = "https://api.opencagedata.com/geocode/v1/json";
    private static final String API_KEY = "f46f5530e2d94a80be246052f380a172";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Coordinates> fetchCoordinates(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }

        // Build query string
        String query = String.format("%s %s, %s %s, %s",
                address.street(),
                address.number(),
                address.postalCode(),
                address.city(),
                address.country()
        );

        String url = UriComponentsBuilder.fromUriString(OPENCAGE_URL)
                .queryParam("q", URLEncoder.encode(query, StandardCharsets.UTF_8))
                .queryParam("key", API_KEY)
                .queryParam("limit", 1)
                .queryParam("no_annotations", 1)
                .build()
                .toUriString();

        log.info("Attempting to geocode URL: {}", url);

        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            log.debug("Raw OpenCage API response: {}", rawResponse);

            ResponseEntity<OpenCageResponse> response = restTemplate.getForEntity(url, OpenCageResponse.class);

            if (response.getBody() != null &&
                    response.getBody().results != null &&
                    response.getBody().results.length > 0) {

                double lat = response.getBody().results[0].geometry.lat;
                double lon = response.getBody().results[0].geometry.lng;

                log.info("Geocoding successful: {} -> lat={}, lon={}", address, lat, lon);
                return Optional.of(new Coordinates(lat, lon));
            }

            log.warn("No geocoding results found for address: {}", address);
            return Optional.empty();

        } catch (Exception e) {
            log.error("Error calling OpenCage API for address {}: {}", address, e.getMessage());
            return Optional.empty();
        }
    }

    // Classes to map OpenCage JSON response
    public static class OpenCageResponse {
        public OpenCageResult[] results;
    }

    public static class OpenCageResult {
        public Geometry geometry;
    }

    public static class Geometry {
        public double lat;
        public double lng;
    }
}
