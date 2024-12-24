package ru.student.familyfinance.Model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String username;
    String fullName;
    String password;
    String email;
    int role;
    boolean blocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority;
        switch (role) {
            case 0:
                simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
                break;
            case 1:
                simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
                break;
            default:
                simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        }
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return username;
    }

}
