package ru.student.familyfinance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.student.familyfinance.Repository.IncomeRepository;
import ru.student.familyfinance.Service.IncomeService;
import ru.student.familyfinance.Service.Implementation.IncomeServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    IncomeRepository repository;

    IncomeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new IncomeServiceImplementation(repository);
    }

    @Test
    @DisplayName("Добавление новой категории дохода")
    public void addIncomeTest() {
        
    }

}
