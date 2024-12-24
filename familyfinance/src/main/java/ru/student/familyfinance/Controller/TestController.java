package ru.student.familyfinance.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test Controller", description = "Контроллер для тестирования работоспособности приложения")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "Если приложение запущено, то вернет слово 'TEST'", tags = "Test Controller")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Приложение запущено", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {@ExampleObject(value = "TEST")})})})
    @GetMapping
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }

}
