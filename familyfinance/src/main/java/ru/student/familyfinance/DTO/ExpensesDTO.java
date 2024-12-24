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
@Schema(name = "Expenses DTO", description = "Класс описывает виды расходов пользователя")
public class ExpensesDTO {

    @Schema(name = "ID", description = "Идентификатор видов расходов", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя, которому принадлежит вид расходов")
    long person_id;

    @Schema(name = "Name", description = "Наименование вида расходов", example = "Продукты питания")
    String name;
    
    @Schema(name = "Expenses Type ID", description = "Идентификатор типа, которому принажлежит вид расходов", example = "1")
    long expensesType_id;

}
