package ru.student.familyfinance.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance.Configuration.SecurityConfiguration;
import ru.student.familyfinance.DTO.ExpensesDTO;
import ru.student.familyfinance.Mapper.MapperExpenses;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.ExpensesService;
import ru.student.familyfinance.Service.ExpensesTypeService;

@WebMvcTest(ExpensesController.class)
@Import(SecurityConfiguration.class)
public class ExpensesControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<ExpensesType> listExpensesType;
    private List<Expenses> listExpenses;
    private List<ExpensesDTO> listExpensesDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;
    
    @MockitoBean
    private ExpensesService service;

    @MockitoBean
    private ExpensesTypeService expensesTypeService;

    @MockitoBean
    private MapperExpenses mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        listExpensesType = List.of(new ExpensesType(1, "First Expenses Type"),
                                   new ExpensesType(2, "Second Expenses Type"),
                                   new ExpensesType(3, "Next Expenses Type"));

        listExpenses = List.of(new Expenses(1, person, "First Expenses", listExpensesType.get(0)),
                               new Expenses(2, person, "Second Expenses", listExpensesType.get(0)),
                               new Expenses(3, person, "Next Expenses", listExpensesType.get(1)),
                               new Expenses(4, person, "Expenses Too", listExpensesType.get(1)),
                               new Expenses(5, person, "Test Expenses", listExpensesType.get(2)),
                               new Expenses(6, person, "Last Expenses", listExpensesType.get(2)));

        listExpensesDTO = List.of(new ExpensesDTO(1, 1, "First Expenses", 1),
                                  new ExpensesDTO(2, 1, "Second Expenses", 1),
                                  new ExpensesDTO(3, 1, "Next Expenses", 2),
                                  new ExpensesDTO(4, 1, "Expenses Too", 2),
                                  new ExpensesDTO(5, 1, "Test Expenses", 3),
                                  new ExpensesDTO(6, 1, "Last Expenses", 3));

    }

    @Test
    @DisplayName("Получение списка видов расходов пользователя")
    @WithMockUser
    public void getExpensesTest() throws Exception {

        doReturn(listExpenses).when(service).getExpenses(person);
        doReturn(listExpensesDTO).when(mapper).toListExpensesDTO(listExpenses);
        String json = jsonMapper.writeValueAsString(listExpensesDTO);

        mvc.perform(get("/expenses").principal(authenticationToken)).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(mapper,Mockito.times(1)).toListExpensesDTO(listExpenses);
        Mockito.verify(service,Mockito.times(1)).getExpenses(person);
    }

    @Test
    @DisplayName("Получение вида расходов по ID")
    @WithMockUser
    public void getExpensesByIdTest() throws Exception {
        doReturn(listExpenses.get(0)).when(service).getExpensesById(1);
        doReturn(listExpensesDTO.get(0)).when(mapper).toExpensesDTO(listExpenses.get(0));
        String json = jsonMapper.writeValueAsString(listExpensesDTO.get(0));
        
        mvc.perform(get("/expenses/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(mapper,Mockito.times(1)).toExpensesDTO(listExpenses.get(0));
        Mockito.verify(service,Mockito.times(1)).getExpensesById(1);
    }

    @Test
    @DisplayName("Добавление нового вида расходов")
    @WithMockUser
    public void postExpensesTest() throws Exception {
        doReturn(listExpenses.get(0)).when(mapper).toExpenses(listExpensesDTO.get(0));
        doReturn(listExpensesType.get(0)).when(expensesTypeService).getExpensesTypeById(1);
        doReturn(true).when(service).addExpenses(listExpenses.get(0));
        String json = jsonMapper.writeValueAsString(listExpensesDTO.get(0));

        mvc.perform(post("/expenses").principal(authenticationToken)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(json))
                                     .andDo(print())
                                     .andExpect(status().isOk());

        Mockito.verify(service,Mockito.times(1)).addExpenses(listExpenses.get(0));
        Mockito.verify(mapper,Mockito.times(1)).toExpenses(listExpensesDTO.get(0));
        Mockito.verify(expensesTypeService, Mockito.times(1)).getExpensesTypeById(1);
    }

    @Test
    @DisplayName("Изменение нового вида расходов")
    @WithMockUser
    public void putExpensesTest() throws Exception {
        doReturn(listExpenses.get(0)).when(mapper).toExpenses(listExpensesDTO.get(0));
        doReturn(listExpensesType.get(0)).when(expensesTypeService).getExpensesTypeById(1);
        doReturn(true).when(service).editExpenses(listExpenses.get(0));
        String json = jsonMapper.writeValueAsString(listExpensesDTO.get(0));

        mvc.perform(put("/expenses").principal(authenticationToken)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(json))
                                     .andDo(print())
                                     .andExpect(status().isOk());

        Mockito.verify(service,Mockito.times(1)).editExpenses(listExpenses.get(0));
        Mockito.verify(mapper,Mockito.times(1)).toExpenses(listExpensesDTO.get(0));
        Mockito.verify(expensesTypeService, Mockito.times(1)).getExpensesTypeById(1);
    }

    @Test
    @DisplayName("Удаление вида расходов")
    @WithMockUser
    public void deleteExpensesTest() throws Exception {
        doReturn(true).when(service).removeExpenses(1);

        mvc.perform(delete("/expenses/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeExpenses(1);
    }

}
