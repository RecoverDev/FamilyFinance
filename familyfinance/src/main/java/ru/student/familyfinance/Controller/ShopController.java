package ru.student.familyfinance.Controller;

import java.util.List;

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
import ru.student.familyfinance.DTO.ShopDTO;
import ru.student.familyfinance.Mapper.MapperShop;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Service.ShopService;

@Tag(name = "Shop Controller", description = "API по работе с магазинами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    public final ShopService service;
    public final MapperShop mapper;

    @Operation(summary = "Получение списка магазинов пользователя", tags = "Shop Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список магазинов пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ShopDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка магазинов пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShops(@AuthenticationPrincipal Person person) {
        List<Shop> shops = service.getShops(person);
        List<ShopDTO> response = mapper.toListShopDTO(shops);
        return response.size() != 0 ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение магазина пользователя по ID", tags = "Shop Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен магазин пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ShopDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Магазин польвателя с таким ID не найден", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable(name="id") long id) {
        Shop shop = service.getShopById(id);
        ShopDTO response = mapper.toShopDTO(shop);
        return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового магазина пользователя", tags = "Shop Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Магазин успешно добавлен", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ShopDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении магазина пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ShopDTO> postShop(@RequestBody ShopDTO shopDTO) {
        Shop shop = mapper.toShop(shopDTO);
        Shop result = service.addShop(shop);
        ShopDTO response = mapper.toShopDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение магазина пользователя", tags = "Shop Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Магазин успешно изменен", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ShopDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении товара пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ShopDTO> putShop(@RequestBody ShopDTO shopDTO) {
        Shop shop = mapper.toShop(shopDTO);
        Shop result = service.editShop(shop);
        ShopDTO response = mapper.toShopDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление магазина пользователя по ID", tags = "Shop Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Магазин успешно удален", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении магазина пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(name="id") long id) {
        boolean result = service.removeShop(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
