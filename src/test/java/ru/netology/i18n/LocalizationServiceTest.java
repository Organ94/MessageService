package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LocalizationServiceTest {

    @ParameterizedTest
    @DisplayName("тест для проверки возвращаемого текста")
    @MethodSource("country")
    void locale(String expected, String param) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertEquals(expected,
                localizationService.locale(Country.valueOf(param)));
    }

    static Stream<Arguments> country() {
        return Stream.of(
                arguments("Welcome", "USA"),
                arguments("Добро пожаловать", "RUSSIA"),
                arguments("Welcome", "BRAZIL")
        );
    }
}