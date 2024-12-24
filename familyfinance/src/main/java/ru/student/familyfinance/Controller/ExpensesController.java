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
import ru.student.familyfinance.DTO.ExpensesDTO;
import ru.student.familyfinance.Mapper.MapperExpenses;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.ExpensesService;
import ru.student.familyfinance.Service.ExpensesTypeService;

@Tag(name = "Expenses Controller", description = "API по работе с категориями расходов")
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {
    private final ExpensesService service;
    private final ExpensesTypeService expensesTypeService;
    private final MapperExpenses mapper;

    @Operation(summary = "Получение списка категорий расходов пользователя", tags = "Expenses Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список категорий расходов ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ExpensesDTO.class)))}),
                           @ApiResponse(responseCode = "404", description = "Ошибка получения списка категорий расходов пользователя", content = @Content)})
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ExpensesDTO>> getExpenses(@AuthenticationPrincipal Person person) {
        List<Expenses> expenses = service.getExpenses(person);
        List<ExpensesDTO> result = mapper.toListExpensesDTO(expenses);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение категории расходов пользователя по ID", tags = "Expenses Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена искомая категория расходов ",
                                        content = {@Content(mediaType = "application/json", 
                                    schema = @Schema(implementation = ExpensesDTO.class))}),
                           @ApiResponse(responseCode = "404", description = "Ошибка получения категории расходов пользователя", content = @Content)})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExpensesDTO> getExpensesById(@PathVariable(name="id") long id) {
        Expenses expenses = service.getExpensesById(id);
        ExpensesDTO result = mapper.toExpensesDTO(expenses);
        return result != null ? new ResponseEntity<>(result,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой категории расходов пользователя", tags = "Expenses Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория расходов успешно добавлена", content = @Content),
                            @ApiResponse(responseCode = "404", description = "Неверный код ExpensesType_id", content = @Content),
                            @ApiResponse(responseCode = "304", description = "неудача при добавлении категории расходов пользователя", content = @Content)})
    @PostMapping
    @PreAuthorize("hasPole('ROLE_USER') or hasPole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> postExpenses(@RequestBody ExpensesDTO expensesDTO, @AuthenticationPrincipal Person person) {
        Expenses expenses = mapper.toExpenses(expensesDTO);
        expenses.setPerson(person);
        ExpensesType expensesType = expensesTypeService.getExpensesTypeById(expensesDTO.getExpensesType_id());
        if (expensesType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        expenses.setExpensesType(expensesType);
        boolean result = service.addExpenses(expenses);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Обновление категории расходов пользователя", tags = "Expenses Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория расходов успешно обновлена", content = @Content),
                            @ApiResponse(responseCode = "404", description = "Неверный код ExpensesType_id", content = @Content),
                            @ApiResponse(responseCode = "304", description = "неудача при обновлении категории расходов пользователя", content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> putExpenses(@RequestBody ExpensesDTO expensesDTO, @AuthenticationPrincipal Person person) {
        Expenses expenses = mapper.toExpenses(expensesDTO);
        ExpensesType expensesType = expensesTypeService.getExpensesTypeById(expensesDTO.getExpensesType_id());
        if (expensesType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        expenses.setPerson(person);
        expenses.setExpensesType(expensesType);
        boolean result = service.editExpenses(expenses);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    
    @Operation(summary = "Удаление категории расходов пользователя", tags = "Expenses Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория расходов успешно удалена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "неудача при удалении категории расходов пользователя", content = @Content)})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteExpenses(@PathVariable(name="id") long id) {
        boolean result = service.removeExpenses(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
