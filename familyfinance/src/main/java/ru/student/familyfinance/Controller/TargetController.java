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
import ru.student.familyfinance.DTO.TargetDTO;
import ru.student.familyfinance.Mapper.MapperTarget;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Service.TargetService;

@Tag(name = "Target Controller", description = "API по работе с целями пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/targets")
public class TargetController {
    private final TargetService service;
    private final MapperTarget mapper;

    @Operation(summary = "Получение списка целей пользователя", tags = "Target Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список целей пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TargetDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка целей пользователя", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TargetDTO>> getTargets(@AuthenticationPrincipal Person person) {
        List<Target> result = service.getTarget(person);
        List<TargetDTO> targetsDTO = mapper.toListTargetDTO(result);
        return targetsDTO.size() > 0 ? new ResponseEntity<>(targetsDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение цели пользователя по ID", tags = "Target Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена цель пользователя",
                                        content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TargetDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Цель польвателя с таким ID не найден", content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TargetDTO> getTargetById(@PathVariable(name="id") long id) {
        Target result = service.getTargetById(id);
        TargetDTO targetDTO = mapper.toTargetDTO(result);
        return targetDTO != null ? new ResponseEntity<>(targetDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой цели пользователя", tags = "Target Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Цель успешно добавлена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении цели пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TargetDTO> postTarget(@AuthenticationPrincipal Person person, @RequestBody TargetDTO targetDTO) {
        Target target = mapper.toTarget(targetDTO);
        target.setPerson(person);
        Target result = service.addTarget(target);
        TargetDTO response = mapper.toTargetDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Изменение цели пользователя", tags = "Target Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Цель успешно изменена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении цели пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TargetDTO> putTarget(@AuthenticationPrincipal Person person, @RequestBody TargetDTO targetDTO) {
        Target target = mapper.toTarget(targetDTO);
        target.setPerson(person);
        Target result = service.editTarget(target);
        TargetDTO response = mapper.toTargetDTO(result);
        return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление цели пользователя по ID", tags = "Target Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Цель успешно удалена", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при удалении цели пользователя" ,content = @Content)})
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTarget(@PathVariable(name="id") long id) {
        boolean result = service.removeTarget(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
