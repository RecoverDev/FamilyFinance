package ru.student.familyfinance_desktop.Storage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthStorageTest {
    private Storage storage = new StorageImplementation();

    @Test
    @DisplayName("Сохранение данных пользователя")
    public void saveCreditionalTest() {
        boolean result = storage.saveCreditional("param", "value");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Чтение данных пользователя")
    public void loadCreditionalTest() {
        String result = storage.loadCreditional("param");
        assertThat(result).isEqualTo("value");
    }

}
