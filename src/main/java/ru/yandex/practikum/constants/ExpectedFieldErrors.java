package ru.yandex.practikum.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExpectedFieldErrors {
    FIRST_NAME_ERROR("Введите корректное имя"),
    LAST_NAME_ERROR("Введите корректную фамилию"),
    ADDRESS_ERROR("Введите корректный адрес"),
    STATION_ERROR("Выберите станцию"),
    PHONE_NUMBER_ERROR("Введите корректный номер");

    private final String errorMessage;
}
