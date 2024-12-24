package ru.student.familyfinance.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Service.ExpensesTypeService;

@Tag(name = "Expenses Type Controller", description = "API по работе с типами категорий расходов")
@RestController
@RequestMapping("/expensestypes")
@RequiredArgsConstructor
public class ExpensesTypeController {
    private final ExpensesTypeService service;

    
    @Operation(summary = "Получение списка типов категорий расходов", tags = "Expenses Type Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список типов категорий расходов ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ExpensesType.class)))}),
                           @ApiResponse(responseCode = "404", description = "Ошибка получения списка типов категорий расходов", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ExpensesType>> getExpensesTypes() {
        List<ExpensesType> result = (List<ExpensesType>)service.getExpensesType();
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение типа категории расходов по идентификатору", tags = "Expenses Type Controller")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Получен тип категории расходов", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExpensesType.class))}),
                            @ApiResponse(responseCode = "404", description = "Тип категории расходов с таким ID не найден", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ExpensesType> getExpensesTypeById(@PathVariable(name="id") long id) {
        ExpensesType result = service.getExpensesTypeById(id);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового типа расходов", tags = "Expenses Type Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Новый тип расходов успешно добавлен ", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении нового типа расходов" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> addExpensesType(@RequestBody ExpensesType expensesType) {
        boolean result = service.addExpensesType(expensesType);
        return result ?  new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение типа расходов", tags = "Expenses Type Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Тип расходов успешно изменен", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении типа расходов" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> putExpensesType(@RequestBody ExpensesType expensesType) {
        boolean result = service.editExpensesType(expensesType);
        return result ?  new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление типа расходов", tags = "Expenses Type Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Тип расходов успешно удален", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении типа расходов" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExpensesType(@PathVariable(name="id") long id) {
        boolean result = service.removeExpensesType(id);
        return result ?  new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
