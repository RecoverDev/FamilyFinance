package ru.student.familyfinance.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.TargetRepository;
import ru.student.familyfinance.Service.Implementation.TargetServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class TargetServiceTest {

    @Mock
    TargetRepository repository;

    TargetService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new TargetServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление новой цели")
    public void addTargetTest() {
        Target target = new Target(1, new Person(), "Test Target", 100.0, LocalDate.of(2024, 10, 1));
        doReturn(target).when(repository).save(target);
        assertThat(service.addTarget(target)).isEqualTo(target);
        Mockito.verify(repository,Mockito.times(1)).save(target);
    }

    @Test
    @DisplayName("Редактирование новой цели")
    public void editTargetTest() {
        Target target = new Target(1, new Person(), "Test Target", 100.0, LocalDate.of(2024, 10, 1));
        doReturn(target).when(repository).save(target);
        assertThat(service.editTarget(target)).isEqualTo(target);
        Mockito.verify(repository,Mockito.times(1)).save(target);
    }

    @Test
    @DisplayName("Удаление цели по ID")
    public void deleteTargetByIdTest() {
        doReturn(false).when(repository).existsById(1L);
        assertThat(service.removeTarget(1)).isTrue();
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
        Mockito.verify(repository,Mockito.times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Получение списка целей пользователя")
    public void getTargetsTest() {
        Person person = new Person();
        List<Target> list = List.of(new Target(1, person, "Test Target", 100.0, LocalDate.of(2024, 10, 1)),
                                    new Target(2, person, "Second Test Target", 200.0, LocalDate.of(2024, 11, 1)),
                                    new Target(3, person, "Test Target too", 300.0, LocalDate.of(2024, 11, 10)));
        doReturn(list).when(repository).findByPerson(person);
        assertThat(service.getTarget(person)).isEqualTo(list);
        Mockito.verify(repository,Mockito.times(1)).findByPerson(person);
    }

    @Test
    @DisplayName("Получение цели пользователя по ID")
    public void getTargetByIdTest() {
        Target target = new Target(1, new Person(), "Test Target", 100.0, LocalDate.of(2024, 10, 1));
        doReturn(Optional.of(target)).when(repository).findById(1L);
        assertThat(service.getTargetById(1L)).isEqualTo(target);
        Mockito.verify(repository,Mockito.times(1)).findById(1L);
    }
}
