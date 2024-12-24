package ru.student.familyfinance.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Person DTO", description = "Класс описывает пользователя системы")
public class PersonDTO {

    @Schema(name = "ID", description = "Идентификатор пользователя а БД", example = "1")
    long id;

    @Schema(name = "Username", description = "Имя пользователя (логин)", example = "first")
    String username;

    @Schema(name = "FullName", description = "Полное имя пользователя", example = "First User")
    String fullName;

    @Schema(name = "E-Mail", description = "Электронный адрес пользователя")
    String email;
    
    @Schema(name = "Role", description = "Роль пользователя в системе (0 - пользователь, 1 - администратор)", example = "0")
    int role;

    @Schema(name = "Blocked", description = "Статус пользователя в системе true - доступен/false - заблокирован")
    boolean blocked;

}
