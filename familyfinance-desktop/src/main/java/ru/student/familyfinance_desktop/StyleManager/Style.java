package ru.student.familyfinance_desktop.StyleManager;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
/**
 * Класс описывает стиль пользовательского интерфейса
 */
public class Style {
    /**
     * Пользовательское имя стиля
     */
    private String description;
    /**
     * Имя файла с описанием стиля CSS
     */
    private String fileName;
    /**
     * Признак выбора стиля по умолчанию
     */
    private boolean defaultStyle;

    @Override
    public String toString() {
        return this.description;
    }
}
