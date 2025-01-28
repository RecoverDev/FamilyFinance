package ru.student.familyfinance.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.PersonRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PersonRepository repository) {
        return username -> {
            Person person = repository.findByUsername(username);
            if (person != null)
                return person;

            throw new UsernameNotFoundException("Пользователь " + username + " не найден");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
                                 AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain (HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests((autorize) -> 
                autorize.requestMatchers("/registration", "/test","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
                .csrf(cs -> cs.disable())
                .httpBasic(Customizer.withDefaults())
                .build();

    }

}
