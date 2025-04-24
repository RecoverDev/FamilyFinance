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
@Schema(name = "Terget DTO", description = "Корзина для формирования списка покупок")
public class BasketDTO {

    @Schema(name = "ID", description = "Идентификатор записи в корзине", example = "1")
    long id;

    @Schema(name = "Person ID", description = "Идентификатор пользователя", example = "1")
    long person_id;

    @Schema(name = "Product ID", description = "Идентификатор товара", example = "1")
    long product_id;

    @Schema(name = "Shop ID", description = "Идентификатор магазина", example = "1")
    long shop_id;
}
