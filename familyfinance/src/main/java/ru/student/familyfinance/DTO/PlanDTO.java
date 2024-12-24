package ru.student.familyfinance.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Plan DTO", description = "Класс описывает пункт плана пользователя")
public class PlanDTO {

    @Schema(name = "ID", description = "Идентификатор пункта плана", example = "1")
    long id;

    @Schema(name = "Date of Operation", description = "Первое число месяца планирования", example = "2024-10-01")
    LocalDate dateOfOperation;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    @Schema(name = "Target ID", description = "Идентификатор цели", example = "1")
    long target_id;

    @Schema(name = "Expenses ID", description = "Идентификатор категории расходов", example = "1")
    long expenses_id;

    @Schema(name = "Income ID", description = "Идентификатор категории доходов", example = "1")
    long income_id;

    @Schema(name = "Summ", description = "Планируемая сумма", example = "100.00")
    double summ;

}
