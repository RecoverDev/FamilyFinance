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
@Schema(name = "Terget DTO", description = "Список магазинов для формирования списка покупок")
public class ShopDTO {


    @Schema(name = "ID", description = "Идентификатор магазина", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    /**
     * Наименование магазина
     */
    @Schema(name = "Name", description = "Наименование магазина", example = "Пятерочка")
    String name;
}
