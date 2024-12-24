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
import ru.student.familyfinance.DTO.IncomeDTO;
import ru.student.familyfinance.Mapper.MapperIncome;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.IncomeService;

@Tag(name = "Income Controller", description = "API по работе с категориями доходов")
@RequiredArgsConstructor
@RestController
@RequestMapping("/incomes")
public class IncomeController {
    private final IncomeService service;
    private final MapperIncome mapper;

    @Operation(summary = "Получение списка категорий доходов пользователя", tags = "Income Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список категорий доходов пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = IncomeDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка категорий доходов пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getImcomes(@AuthenticationPrincipal Person person) {
        List<Income> result = service.getIncomes(person);
        List<IncomeDTO> incomesDTO = mapper.toListIncomeDTO(result);
        return result.size() > 0 ? new ResponseEntity<>(incomesDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение категории дохода по ID", tags = "Income Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена категория дохода",
                                        content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IncomeDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Категория дохода с таким ID не найдена", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable(name="id") long id) {
        Income result = service.getIncomeById(id);
        IncomeDTO incomeDTO = mapper.toIncomeDTO(result);
        return result != null ? new ResponseEntity<>(incomeDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой категории дохода пользователя", tags = "Income Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория дохода успешно добавлена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении категории дохода пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> postIncome(@RequestBody IncomeDTO incomeDTO, @AuthenticationPrincipal Person person) {
        Income income = mapper.toIncome(incomeDTO);
        income.setPerson(person);
        boolean result = service.addIncome(income);
        return result ?  new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение категории дохода пользователя", tags = "Income Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория дохода успешно изменена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении категории дохода пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> putIncome(@RequestBody IncomeDTO incomeDTO, @AuthenticationPrincipal Person person) {
        Income income = mapper.toIncome(incomeDTO);
        income.setPerson(person);
        boolean result = service.editIncome(income);
        return result ?  new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление категории дохода пользователя по ID", tags = "Income Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Категория дохода успешно удалена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении категории дохода пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteIncome(@PathVariable(name="id") long id) {
        boolean result = service.removeIncome(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
