package ru.student.familyfinance.Controller;

import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.DTO.BasketDTO;
import ru.student.familyfinance.Mapper.MapperBasket;
import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.BasketService;

@Tag(name = "Basket Controller", description = "API по работе с закупками пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/baskets")
public class BasketController {
    private final BasketService service;
    private final MapperBasket  mapper;

    @Operation(summary = "Получение списка покупок пользователя", tags = "Basket Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список покупок пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = BasketDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка покупок пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<BasketDTO>> getBaskets(@AuthenticationPrincipal Person person) {
        List<Basket> baskets = service.getBasket(person);
        List<BasketDTO> response = mapper.toListBasketDTO(baskets);
        return response.size() != 0 ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение запланированной покупки пользователя по ID", tags = "Basket Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена зпланированная покупка пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = BasketDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Закупка польвателя с таким ID не найден", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BasketDTO> getBasketById(@PathVariable(name="id") long id) {
        Basket basket = service.getBasketById(id);
        BasketDTO response = mapper.toBasketDTO(basket);
        return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой закупки пользователя", tags = "Basket Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Закупка успешно добавлена", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = BasketDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении закупки пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BasketDTO> postShop(@RequestBody BasketDTO basketDTO) {
        Basket basket = mapper.toBasket(basketDTO);
        Basket result = service.addBasket(basket);
        BasketDTO response = mapper.toBasketDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение закупки пользователя", tags = "Basket Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Закупка успешно изменена", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = BasketDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении закупки пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BasketDTO> putShop(@RequestBody BasketDTO basketDTO) {
        Basket basket = mapper.toBasket(basketDTO);
        Basket result = service.editBasket(basket);
        BasketDTO response = mapper.toBasketDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление закупки пользователя по ID", tags = "Basket Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Магазин успешно удален", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении магазина пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(name="id") long id) {
        boolean result = service.removeBasket(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/purchase")
    public ResponseEntity<HttpStatus> createPurchase(@AuthenticationPrincipal Person person, @RequestBody List<Pair<BasketDTO,Double>> list) {
        List<Pair<Basket, Double>> purchases = list.stream().map(p -> Pair.of(mapper.toBasket(p.getFirst()), p.getSecond())).toList();
        boolean result = service.makePurchase(person, purchases);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
