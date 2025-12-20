package ru.student.familyfinance_service.Model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Текущий пользователь системы
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Person {

    long id;

    String username;

    String fullName;

    String email;
    
    int role;

    boolean blocked;

    public void load(Person person) {
        this.id = person.getId();
        this.username = person.getUsername();
        this.fullName = person.getFullName();
        this.email = person.getEmail();
        this.role = person.getRole();
        this.blocked = person.isBlocked();
    }
}
