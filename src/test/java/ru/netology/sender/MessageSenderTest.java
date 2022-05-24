package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class MessageSenderTest {

    @ParameterizedTest
    @MethodSource("argumentsForTheMessageSenderTest")
    @DisplayName("Тест на оправку сообщения")
    void test_messageSender_mockito(String expected, String param1, String param2, Location param3) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(param1))
                .thenReturn(param3);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.valueOf(param2)))
                .thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, param1);
        Assertions.assertEquals(expected, messageSender.send(headers));
        System.out.println();
    }

    static Stream<Arguments> argumentsForTheMessageSenderTest() {
        return Stream.of(
                arguments("Welcome", "96.", "USA", new Location("New York", Country.USA, null,  0)),
                arguments("Добро пожаловать", "172.", "RUSSIA", new Location("Moscow", Country.RUSSIA, null, 0))
        );
    }
}