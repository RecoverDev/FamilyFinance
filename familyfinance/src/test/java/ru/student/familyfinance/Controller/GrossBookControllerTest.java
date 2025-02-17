package ru.student.familyfinance.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import ru.student.familyfinance.DTO.GrossBookDTO;
import ru.student.familyfinance.DTO.TargetDTO;
import ru.student.familyfinance.Mapper.MapperGrossBook;
import ru.student.familyfinance.Mapper.MapperTarget;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.GrossBookService;

@WebMvcTest(GrossBookConrtoller.class)
@Import(SecurityConfiguration.class)
public class GrossBookControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<GrossBook> listGrossBooks;
    private List<GrossBookDTO> listGrossBooksDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private GrossBookService service;

    @MockitoBean
    private GrossBookBuilder builder;

    @MockitoBean
    private MapperGrossBook mapper;

    @MockitoBean
    private MapperTarget targetMapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        listGrossBooks = getListGrossBooks();
        listGrossBooksDTO = getListGrossBooksDTO();

    }

    @Test
    @DisplayName("Получение всех записей пользователя за период")
    @WithMockUser
    public void getGrossBooksTest() throws Exception {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 30);
        doReturn(listGrossBooks).when(service).getGrossBooks(begin, end, person);
        doReturn(listGrossBooksDTO).when(mapper).toListGrossBookDTO(listGrossBooks);
        String json = jsonMapper.writeValueAsString(listGrossBooksDTO);

        mvc.perform(get("/grossbooks/2024-01-01/2024-01-30").principal(authenticationToken)).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getGrossBooks(begin, end, person);
        Mockito.verify(mapper, Mockito.times(1)).toListGrossBookDTO(listGrossBooks);
    }

    @Test
    @DisplayName("Добавление новой записи в журнал пользователя")
    @WithMockUser
    public void postGrossBookTest() throws Exception {
        doReturn(listGrossBooks.get(0)).when(service).addGrossBook(listGrossBooks.get(0));
        doReturn(listGrossBooks.get(0)).when(builder).buildGrossBook(person, listGrossBooksDTO.get(0));
        doReturn(listGrossBooksDTO.get(0)).when(mapper).toGrossBookDTO(listGrossBooks.get(0));

        String json = jsonMapper.writeValueAsString(listGrossBooksDTO.get(0));

        mvc.perform(post("/grossbooks").principal(authenticationToken)
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .accept(MediaType.APPLICATION_JSON)
                                       .content(json))
                                       .andDo(print())
                                       .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addGrossBook(listGrossBooks.get(0));
        Mockito.verify(builder, Mockito.times(1)).buildGrossBook(person, listGrossBooksDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toGrossBookDTO(listGrossBooks.get(0));
    }

    @Test
    @DisplayName("Изменение записи в журнале пользователя")
    @WithMockUser
    public void putGrossBookTest() throws Exception {
        doReturn(listGrossBooks.get(0)).when(service).editGrossBook(listGrossBooks.get(0));
        doReturn(listGrossBooks.get(0)).when(builder).buildGrossBook(person, listGrossBooksDTO.get(0));
        doReturn(listGrossBooksDTO.get(0)).when(mapper).toGrossBookDTO(listGrossBooks.get(0));
        String json = jsonMapper.writeValueAsString(listGrossBooksDTO.get(0));

        mvc.perform(put("/grossbooks").principal(authenticationToken)
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .accept(MediaType.APPLICATION_JSON)
                                       .content(json))
                                       .andDo(print())
                                       .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editGrossBook(listGrossBooks.get(0));
        Mockito.verify(builder, Mockito.times(1)).buildGrossBook(person, listGrossBooksDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toGrossBookDTO(listGrossBooks.get(0));
    }

    @Test
    @DisplayName("Удаление записи в журнале пользователя")
    @WithMockUser
    public void deleteGrossBookTest() throws Exception {
        doReturn(true).when(service).removeGrossBook(1);

        mvc.perform(delete("/grossbooks/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeGrossBook(1);
    }

    @Test
    @DisplayName("Получение всех записей о доходах за период")
    @WithMockUser
    public void getIncomeGrossBookTest() throws Exception {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 30);
        List<GrossBook> result = listGrossBooks.stream().filter(p -> p.getIncome() != null).toList();
        List<GrossBookDTO> resultDTO = listGrossBooksDTO.stream().filter(p -> p.getIncome_id() > 0).toList();
        doReturn(result).when(service).getListIncomesByPeriod(begin, end, person);
        doReturn(resultDTO).when(mapper).toListGrossBookDTO(result);
        String json = jsonMapper.writeValueAsString(resultDTO);

        mvc.perform(get("/grossbooks/income/2024-01-01/2024-01-30")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getListIncomesByPeriod(begin, end, person);
        Mockito.verify(mapper, Mockito.times(1)).toListGrossBookDTO(result);
    }

    @Test
    @DisplayName("Получение всех записей о расходах за период")
    @WithMockUser
    public void getExpensesGrossBookTest() throws Exception {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 30);
        List<GrossBook> result = listGrossBooks.stream().filter(p -> p.getExpenses() != null).toList();
        List<GrossBookDTO> resultDTO = listGrossBooksDTO.stream().filter(p -> p.getExpenses_id() > 0).toList();
        doReturn(result).when(service).getListExpensesByPeriod(begin, end, person);
        doReturn(resultDTO).when(mapper).toListGrossBookDTO(result);
        String json = jsonMapper.writeValueAsString(resultDTO);

        mvc.perform(get("/grossbooks/expenses/2024-01-01/2024-01-30")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getListExpensesByPeriod(begin, end, person);
        Mockito.verify(mapper, Mockito.times(1)).toListGrossBookDTO(result);
    }

    @Test
    @DisplayName("Получение всех записей о выполнениии целей за период")
    @WithMockUser
    public void getTargetGrossBookTest() throws Exception {
        LocalDate begin = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 30);
        List<GrossBook> result = listGrossBooks.stream().filter(p -> p.getTarget() != null).toList();
        List<GrossBookDTO> resultDTO = listGrossBooksDTO.stream().filter(p -> p.getTarget_id() > 0).toList();
        doReturn(result).when(service).getListTargetByPeriod(begin, end, person);
        doReturn(resultDTO).when(mapper).toListGrossBookDTO(result);
        String json = jsonMapper.writeValueAsString(resultDTO);

        mvc.perform(get("/grossbooks/target/2024-01-01/2024-01-30")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getListTargetByPeriod(begin, end, person);
        Mockito.verify(mapper, Mockito.times(1)).toListGrossBookDTO(result);
    }

    @Test
    @DisplayName("Получение всех записей о выполнении целей по заданному списку целей")
    @WithMockUser
    public void getTargetGrossBookByScroll() throws Exception {
        List<Target> listTarget = List.of(new Target(1, person, "First Target", 100.0, LocalDate.of(2024, 10, 1)),
                                          new Target(2, person, "Second Target", 200.0, LocalDate.of(2024, 10, 1)),
                                          new Target(3, person, "Next Target", 300.0, LocalDate.of(2024, 10, 1)),
                                          new Target(4, person, "Last Target", 400.0, LocalDate.of(2024, 10, 1)));
        List<TargetDTO> listTargetDTO = List.of(new TargetDTO(1, 1, "First Target", 100.0, LocalDate.of(2024, 10, 1)),
                                                new TargetDTO(2, 1, "Second Target", 200.0, LocalDate.of(2024, 10, 1)),
                                                new TargetDTO(3, 1, "Next Target", 300.0, LocalDate.of(2024, 10, 1)),
                                                new TargetDTO(4, 1, "Last Target", 400.0, LocalDate.of(2024, 10, 1)));
        List<GrossBook> result = listGrossBooks.stream().filter(p -> p.getTarget() != null).toList();
        List<GrossBookDTO> resultDTO = listGrossBooksDTO.stream().filter(p -> p.getTarget_id() > 0).toList();
        doReturn(listTarget).when(targetMapper).toListTargets(listTargetDTO);
        doReturn(resultDTO).when(mapper).toListGrossBookDTO(result);
        doReturn(result).when(service).getListTargetByScroll(listTarget, person);
        String json = jsonMapper.writeValueAsString(listTargetDTO);
        String response = jsonMapper.writeValueAsString(resultDTO);

        mvc.perform(post("/grossbooks/target/list").principal(authenticationToken)
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .accept(MediaType.APPLICATION_JSON)
                                       .content(json))
                                       .andDo(print())
                                       .andExpect(status().isOk()).andExpect(content().json(response));

        Mockito.verify(targetMapper, Mockito.times(1)).toListTargets(listTargetDTO);
        Mockito.verify(mapper, Mockito.times(1)).toListGrossBookDTO(result);
        Mockito.verify(service, Mockito.times(1)).getListTargetByScroll(listTarget, person);
                                  
    }


    private List<GrossBook> getListGrossBooks() {
        List<Income> listIncome = List.of(new Income(1, person, "First Income"),
                                          new Income(2, person, "Second Income"),
                                          new Income(3, person, "Next Income"),
                                          new Income(4, person, "Last Income"));

        List<ExpensesType> listExpensesType = List.of(new ExpensesType(1, "First Expenses Type"),
                                                      new ExpensesType(2, "Second Expenses Type"),
                                                      new ExpensesType(3, "Next Expenses Type"));

        List<Expenses> listExpenses = List.of(new Expenses(1, person, "First Expenses", listExpensesType.get(0)),
                                              new Expenses(2, person, "Second Expenses", listExpensesType.get(0)),
                                              new Expenses(3, person, "Next Expenses", listExpensesType.get(1)),
                                              new Expenses(4, person, "Expenses Too", listExpensesType.get(1)),
                                              new Expenses(5, person, "Test Expenses", listExpensesType.get(2)),
                                              new Expenses(6, person, "Last Expenses", listExpensesType.get(2)));


        List<Target> listTarget = List.of(new Target(1, person, "First Target", 100.0, LocalDate.of(2024, 10, 1)),
                                          new Target(2, person, "Second Target", 200.0, LocalDate.of(2024, 10, 1)),
                                          new Target(3, person, "Next Target", 300.0, LocalDate.of(2024, 10, 1)),
                                          new Target(4, person, "Last Target", 400.0, LocalDate.of(2024, 10, 1)));

        LocalDate date = LocalDate.of(2024, 1, 1);
        List<GrossBook> result = List.of(new GrossBook(1, date, person, listIncome.get(0), null, null, 1000.0),
                                         new GrossBook(2, date.plusDays(1), person, listIncome.get(1), null, null, 2000.0),
                                         new GrossBook(3, date.plusDays(2), person, null, listExpenses.get(0), null, 100.0),
                                         new GrossBook(4, date.plusDays(3), person, null, listExpenses.get(1), null, 100.0),
                                         new GrossBook(5, date.plusDays(4), person, null, listExpenses.get(2), null, 100.0),
                                         new GrossBook(6, date.plusDays(5), person, null, listExpenses.get(3), null, 100.0),
                                         new GrossBook(7, date.plusDays(6), person, null, null, listTarget.get(0), 100.0),
                                         new GrossBook(8, date.plusDays(7), person, null, null, listTarget.get(1), 100.0));

        return result;
    }

    private List<GrossBookDTO> getListGrossBooksDTO() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<GrossBookDTO> result = List.of(new GrossBookDTO(1, date, 1, 0, 0, 1, 1000.0),
                                            new GrossBookDTO(2, date.plusDays(1), 1, 0, 0, 2, 2000.0),
                                            new GrossBookDTO(3, date.plusDays(2), 1, 0, 1, 0, 100.0),
                                            new GrossBookDTO(4, date.plusDays(3), 1, 0, 2, 0, 100.0),
                                            new GrossBookDTO(5, date.plusDays(4), 1, 0, 3, 0, 100.0),
                                            new GrossBookDTO(6, date.plusDays(5), 1, 0, 4, 0, 100.0),
                                            new GrossBookDTO(7, date.plusDays(6), 1, 1, 0, 0, 100.0),
                                            new GrossBookDTO(8, date.plusDays(7), 1, 2, 0, 0, 100.0));

        return result;
    }
}
