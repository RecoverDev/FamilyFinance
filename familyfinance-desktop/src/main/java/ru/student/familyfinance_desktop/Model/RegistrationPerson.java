package ru.student.familyfinance_desktop.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Данные пользователя, передаваемые при регистрации
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPerson {

    /**
     * Имя пользователя (логин)
     */
    String username;

    /**
     * Полное имя пользователя
     */
    String fullName;

    /**
     * E-Mail пользователя
     */
    String email;

    /**
     * Пароль пользователя
     */
    String password;

    @Override
    public String toString() {
        return String.format("UserName: %s, FullName: %s, E-Mail: %s", this.getUsername(), this.getFullName(), this.getEmail());
    }

}
