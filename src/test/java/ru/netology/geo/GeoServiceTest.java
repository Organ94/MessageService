package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeoServiceTest {

    GeoService geoService;

    @ParameterizedTest
    @DisplayName("тест для проверки определения локации по ip")
    @MethodSource("ipAddress")
    void test_geoServiceImpl(String expected, String params) {
        geoService = new GeoServiceImpl();
        Assertions.assertEquals(expected, geoService.byIp(params).getCity());
    }

    @Test
    @DisplayName("тест для проверки определения локации по координатам")
    void test_byCoordinates() {
        geoService = new GeoServiceImpl();
        double latitude = 123.123;
        double longitude = 125.123;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            geoService.byCoordinates(latitude,longitude);
        });
        Assertions.assertEquals("Not implemented", exception.getMessage());
    }

    static Stream<Arguments> ipAddress() {
        return Stream.of(
                arguments("Moscow", "172."),
                arguments("New York", "96."),
                arguments("Moscow", "172.0.32.11"),
                arguments("New York", "96.44.183.149"),
                arguments(null, "127.0.0.1")
        );
    }
}