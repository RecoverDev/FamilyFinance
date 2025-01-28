package ru.student.familyfinance.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.DTO.PersonDTO;
import ru.student.familyfinance.DTO.RegistrationPerson;
import ru.student.familyfinance.Mapper.MapperPerson;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.PersonService;

@Tag(name = "Autorization Controller", description = "Авторизация и регистрация пользователя")
@RequiredArgsConstructor
@RestController
public class AutorizationController {
    private final PasswordEncoder passwordEncoder;
    private final PersonService service;
    private final MapperPerson  mapper;

    @Operation(summary = "Авторизация пользователя", tags = "Autorization Controller")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Пользователь успешно авторизовался", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))})})
    @GetMapping("/login")
    public ResponseEntity<PersonDTO> login(Authentication authentication) {
        Person person = service.getPersonByUsername(authentication.getName());
        PersonDTO personDTO = mapper.toPersonDTO(person);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @Operation(summary = "Регистрация пользователя", tags = "Autorization Controller")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрировался", content = @Content),
                            @ApiResponse(responseCode = "304", description = "Ошибка регистрации пользователя", content = @Content)})
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationPerson registrationPerson) {


        Person person = new Person(0, registrationPerson.getUsername(), 
                                         registrationPerson.getFullName(), 
                                         passwordEncoder.encode(registrationPerson.getPassword()), 
                                         registrationPerson.getEmail(),
                                         0,
                                         false);
        boolean result = service.addPerson(person);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }
}
