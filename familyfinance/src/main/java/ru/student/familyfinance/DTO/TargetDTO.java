package ru.student.familyfinance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

}
