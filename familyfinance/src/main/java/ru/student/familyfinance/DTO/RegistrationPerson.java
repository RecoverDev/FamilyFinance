package ru.student.familyfinance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Registration Person", description = "Описание пользователя, получаемого при регистрации")
public class RegistrationPerson {

    @Schema(name = "UserName", description = "Имя пользователя (логин)", example = "second")
    String username;

    @Schema(name = "FullName", description = "Полное имя пользователя", example = "Second User")
    String fullName;

    @Schema(name = "E-Mail", description = "E-Mail пользователя")
    String email;

    @Schema(name = "Password", description = "Пароль пользователя")
    String password;

}
