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
import ru.student.familyfinance.DTO.PersonDTO;
import ru.student.familyfinance.Mapper.MapperPerson;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.PersonService;

@Tag(name = "Persons Controller", description = "API по работе с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {
    private final PersonService service;
    private final MapperPerson mapper;

    @Operation(summary = "Получение списка пользователей", tags = "Persons Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список пользователей ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))}),
                            @ApiResponse(responseCode = "404", description = "Ошибка при получении списка пользователей", content = @Content)})
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<Person> persons = service.getPersons();
        List<PersonDTO> result = mapper.toListPersonDTO(persons);
        return result.size() > 0 ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение пользователя по ID", tags = "Persons Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен пользователь",
                                        content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDTO.class))}),
                            @ApiResponse(responseCode = "404", description = "Польватель с таким ID не найден", content = @Content)})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable(name="id") long id) {
        Person person = service.getPersonById(id);
        if (person != null) {
            PersonDTO personDTO = mapper.toPersonDTO(person);
            return new ResponseEntity<>(personDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового пользователя", tags = "Persons Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Пользователь успешно добавлен ", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при добавлении пользователя" ,content = @Content)})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> postPerson(@RequestBody PersonDTO personDTO) {
        Person person = mapper.toPerson(personDTO);
        boolean result = service.addPerson(person);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    
    @Operation(summary = "Изменение данных пользователя", tags = "Persons Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Данные пользователя успешно изменены", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка при изменении данных пользователя" ,content = @Content)})
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> putPerson(@RequestBody PersonDTO personDTO) {
        Person person = mapper.toPerson(personDTO);
        boolean result = service.editPerson(person);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление пользователя", tags = "Persons Controller")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", description = "Пользователь успешно удален", content = @Content),
                            @ApiResponse(responseCode = "404", description = "Ошибка при удалении пользователя" ,content = @Content)})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable(name="id") long id) {
        boolean result = service.removePerson(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
