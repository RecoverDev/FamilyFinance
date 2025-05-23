package ru.student.familyfinance.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Terget DTO", description = "Цели для накоплений пользователя")
public class TargetDTO {

    @Schema(name = "ID", description = "Идентификатор цели", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    /**
     * Наименование цели
     */
    @Schema(name = "Name", description = "Наименование цели", example = "Покупка квартиры")
    String name;

    /**
     * Сумма, необходимая для достижения цели
     */
    @Schema(name = "Summ", description = "Сумма, необходимая для достижения цели", example = "100000.00")
    double summ;

    /**
     * Дата установки цели
     */
    @Schema(name = "SettingDate", description = "Дата установки цели", example = "2024-12-31")
    LocalDate settingDate;
}
