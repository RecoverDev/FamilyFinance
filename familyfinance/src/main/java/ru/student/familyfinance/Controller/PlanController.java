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
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.DTO.PlanDTO;
import ru.student.familyfinance.Mapper.MapperPlan;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;
import ru.student.familyfinance.Service.PlanService;

@Tag(name = "Plan Controller", description = "API по работе с планом пользователя")
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService service;
    private final MapperPlan mapper;
    private final Builder builder;

    @Operation(summary = "Получение плана пользователя на определенный месяц", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен план пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PlanDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении плана пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{date}")
    public ResponseEntity<List<PlanDTO>> getPlans(@AuthenticationPrincipal Person person, @PathVariable(name="date") LocalDate date) {
        List<Plan> plans = service.getPlans(person, date);
        List<PlanDTO> result = mapper.toListPlanDTO(plans);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового пункта плана пользователя", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Пункт плана успешно добавлен", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении пункта плана пользователя" ,content = @Content)})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> postPlan(@AuthenticationPrincipal Person person, @RequestBody PlanDTO planDTO) {
        Plan plan = builder.buildPlan(planDTO, person);

        boolean result = service.addPlan(plan);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление пункта плана пользователя", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Пункт плана успешно удален", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении пункта плана пользователя" ,content = @Content)})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deletePlan(@PathVariable(name="id") long id) {
        boolean result = service.removePlan(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение пункта плана пользователя", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Пункт плана успешно изменен", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении пункта плана пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> putPlan(@AuthenticationPrincipal Person person, @RequestBody PlanDTO planDTO) {
        Plan plan = builder.buildPlan(planDTO, person);
        boolean result = service.editPlan(plan);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    
    @Operation(summary = "Получение планируемой суммы дохода пользователя за выбранный месяц", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Сумма дохода успешно получена", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Double.class), examples = {@ExampleObject(value = "100.00")})})})
    @GetMapping("/income/{date}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Double> getIncomePlan(@AuthenticationPrincipal Person person, @PathVariable(name="date") LocalDate date) {
        double result = service.getIncomePlan(person, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Получение планируемой суммы расходов пользователя за выбранный месяц", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Сумма расходов успешно получена", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Double.class), examples = {@ExampleObject(value = "100.00")})})})
    @GetMapping("/expenses/{date}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Double> getExpensesPlan(@AuthenticationPrincipal Person person, @PathVariable(name="date") LocalDate date) {
        double result = service.getExpensesPlan(person, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Получение планируемой суммы расходов для достижения цели пользователя за выбранный месяц", tags = "Plan Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Сумма расходов для достижения цели успешно получена", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Double.class), examples = {@ExampleObject(value = "100.00")})})})
    @GetMapping("/target/{date}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Double> getTargetPlan(@AuthenticationPrincipal Person person, @PathVariable(name="date") LocalDate date) {
        double result = service.getTargetPlan(person, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
