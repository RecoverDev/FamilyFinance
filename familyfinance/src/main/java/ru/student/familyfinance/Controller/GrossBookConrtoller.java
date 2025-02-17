package ru.student.familyfinance.Controller;

import java.time.LocalDate;
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
import ru.student.familyfinance.DTO.GrossBookDTO;
import ru.student.familyfinance.DTO.TargetDTO;
import ru.student.familyfinance.Mapper.MapperGrossBook;
import ru.student.familyfinance.Mapper.MapperTarget;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Service.GrossBookService;

@Tag(name = "GrossBook Controller", description = "API по работе с журналом фактических расходов и доходов пользователя")
@RestController
@RequestMapping("/grossbooks")
@RequiredArgsConstructor
public class GrossBookConrtoller {
    private final GrossBookService service;
    private final GrossBookBuilder builder;
    private final MapperGrossBook mapper;
    private final MapperTarget targerMapper;

    @Operation(summary = "Получение всех записей об операциях пользователя за период", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи об операциях пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GrossBookDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка записей об операциях пользователя", content = @Content)})
    @GetMapping("/{beginDate}/{endDate}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GrossBookDTO>> getGrossBooks(@AuthenticationPrincipal Person person,
                                                            @PathVariable(name="beginDate") LocalDate beginDate,
                                                            @PathVariable(name="endDate") LocalDate endDate) {
        List<GrossBook> grossBooks = service.getGrossBooks(beginDate, endDate, person);
        List<GrossBookDTO> result = mapper.toListGrossBookDTO(grossBooks);
        return result.size() > 0 ? new ResponseEntity<>(result,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой записи в журнал операций пользователя", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "запись успешно добавлена", 
                                        content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = GrossBookDTO.class))}),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении записи об операции пользователя" ,content = @Content)})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GrossBookDTO> postGrossBook(@AuthenticationPrincipal Person person, @RequestBody GrossBookDTO grossBookDTO) {
        GrossBook grossBook = builder.buildGrossBook(person, grossBookDTO);
        GrossBook result = service.addGrossBook(grossBook);
        GrossBookDTO response = mapper.toGrossBookDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение новой записи в журнал операций пользователя", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "запись успешно изменена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении записи об операции пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<GrossBookDTO> putGrossBook(@AuthenticationPrincipal Person person, @RequestBody GrossBookDTO grossBookDTO) {
        GrossBook grossBook = builder.buildGrossBook(person, grossBookDTO);
        GrossBook result = service.editGrossBook(grossBook);
        GrossBookDTO response = mapper.toGrossBookDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление записи в журнале операций пользователя", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "запись успешно удалена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении записи об операции пользователя" ,content = @Content)})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteGrossBook(@PathVariable(name="id") long id) {
        boolean result = service.removeGrossBook(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Получение всех записей о доходах пользователя за период", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи о доходах пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GrossBookDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка записей о доходах пользователя", content = @Content)})
    @GetMapping("/income/{beginDate}/{endDate}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GrossBookDTO>> getIncomeGrossBook(@AuthenticationPrincipal Person person,
                                                                 @PathVariable(name="beginDate") LocalDate beginDate,
                                                                 @PathVariable(name="endDate") LocalDate endDate) {
        List<GrossBook> grossBooks = service.getListIncomesByPeriod(beginDate, endDate, person);
        List<GrossBookDTO> result = mapper.toListGrossBookDTO(grossBooks);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение всех записей о расходах пользователя за период", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи о расходах пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GrossBookDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка записей о расходах пользователя", content = @Content)})
    @GetMapping("/expenses/{beginDate}/{endDate}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GrossBookDTO>> getExpensesGrossBook(@AuthenticationPrincipal Person person,
                                                                 @PathVariable(name="beginDate") LocalDate beginDate,
                                                                 @PathVariable(name="endDate") LocalDate endDate) {
        List<GrossBook> grossBooks = service.getListExpensesByPeriod(beginDate, endDate, person);
        List<GrossBookDTO> result = mapper.toListGrossBookDTO(grossBooks);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение всех записей о целях пользователя за период", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи о целях пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GrossBookDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка записей о целях пользователя", content = @Content)})
    @GetMapping("/target/{beginDate}/{endDate}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GrossBookDTO>> getTargetGrossBook(@AuthenticationPrincipal Person person,
                                                                 @PathVariable(name="beginDate") LocalDate beginDate,
                                                                 @PathVariable(name="endDate") LocalDate endDate) {
        List<GrossBook> grossBooks = service.getListTargetByPeriod(beginDate, endDate, person);
        List<GrossBookDTO> result = mapper.toListGrossBookDTO(grossBooks);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение всех записей о целях пользователя входящий в заданный список", tags = "GrossBook Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи о целях пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GrossBookDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка записей о целях пользователя", content = @Content)})
    @PostMapping("/target/list")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GrossBookDTO>> getTargetByScroll(@AuthenticationPrincipal Person person, @RequestBody List<TargetDTO> targets) {
        List<Target> requestTargets = targerMapper.toListTargets(targets);
        List<GrossBook> result = service.getListTargetByScroll(requestTargets, person);
        List<GrossBookDTO> response = mapper.toListGrossBookDTO(result);
        return !response.isEmpty() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
