package ru.student.familyfinance.DTO;

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
@Schema(name = "Income DTO", description = "Класс описывает виды доходов пользователя")
public class IncomeDTO {

    @Schema(name = "ID", description = "Идентификатор видов доходов", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя, которому принадлежит вид доходов")
    long person_id;

    @Schema(name = "Name", description = "Наименование вида доходов", example = "Заработная плата")
    String name;
}
