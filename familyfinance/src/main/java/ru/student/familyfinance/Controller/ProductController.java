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
import ru.student.familyfinance.DTO.ProductDTO;
import ru.student.familyfinance.Mapper.MapperProduct;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Service.ProductService;

@Tag(name = "Product Controller", description = "API по работе с товарами пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final MapperProduct mapper;

    @Operation(summary = "Получение списка товаров пользователя", tags = "Product Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список товаров пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка товаров пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(@AuthenticationPrincipal Person person) {
        List<Product> products = service.getProduct(person);
        List<ProductDTO> response = mapper.toListProductDTO(products);
        return response.size() > 0 ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение товара пользователя по ID", tags = "Product Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен товар пользователя",
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Товар польвателя с таким ID не найден", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name="id") long id) {
        Product product = service.getProductById(id);
        ProductDTO response = mapper.toProductDTO(product);
        return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового товара пользователя", tags = "Product Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Товар успешно добавлен", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении товара пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        Product result = service.addProduct(product);
        ProductDTO response = mapper.toProductDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение товара пользователя", tags = "Product Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Товар успешно изменен", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении товара пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> putProduct(@RequestBody ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        Product result = service.editProduct(product);
        ProductDTO response = mapper.toProductDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление товара пользователя по ID", tags = "Product Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Товар успешно удален", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении товара пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(name="id") long id) {
        boolean result = service.removeProduct(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
