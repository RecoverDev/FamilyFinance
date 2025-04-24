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
@Schema(name = "Terget DTO", description = "Товар для формирования списка покупок")
public class ProductDTO {

    @Schema(name = "ID", description = "Идентификатор товара", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    /**
     * Наименование товара
     */
    @Schema(name = "Name", description = "Наименование товара", example = "Молоко")
    String name;

    /**
     * Вид расходов товара
     */
    @Schema(name = "Expenses ID", description = "Идентификатор вида расходов", example = "1")
    long expenses_id;
}
