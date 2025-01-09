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
@Schema(name = "GrossBook DTO", description = "Класс описывает фактические расходы и доходы пользователя")
public class GrossBookDTO {

    @Schema(name = "ID", description = "Идентификатор записи", example = "1")
    long id;

    @Schema(name = "Date of Operation", description = "Дата совершения операции", example = "2024-10-01")
    LocalDate dateOfOperation;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    @Schema(name = "Target ID", description = "Идентификатор цели", example = "1")
    long target_id;

    @Schema(name = "Expenses ID", description = "Идентификатор категории расходов", example = "1")
    long expenses_id;

    @Schema(name = "Income ID", description = "Идентификатор категории доходов", example = "1")
    long income_id;

    @Schema(name = "Summ", description = "Сумма операции", example = "100.00")
    double summ;
}
