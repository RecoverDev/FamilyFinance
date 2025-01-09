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
import ru.student.familyfinance.DTO.PlanDTO;
import ru.student.familyfinance.Mapper.MapperPlan;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.PlanService;

@WebMvcTest(PlanController.class)
@Import(SecurityConfiguration.class)
public class PlanControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<Plan> listPlans;
    private List<PlanDTO> listPlanDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private PlanService service;

    @MockitoBean
    private Builder builder;

    @MockitoBean
    private MapperPlan mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        listPlans = getPlans();
        listPlanDTO = getPlansDTO();
    }

    @Test
    @DisplayName("Получение плана пользователя на месяц")
    @WithMockUser
    public void getPlansTest() throws Exception {
        doReturn(listPlans).when(service).getPlans(person, LocalDate.of(2024, 1, 1));
        doReturn(listPlanDTO).when(mapper).toListPlanDTO(listPlans);
        String json = jsonMapper.writeValueAsString(listPlanDTO);

        mvc.perform(get("/plans/2024-01-01")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getPlans(person, LocalDate.of(2024, 1, 1));
        Mockito.verify(mapper, Mockito.times(1)).toListPlanDTO(listPlans);
    }

    @Test
    @DisplayName("Добавление пункта плана пользователя")
    @WithMockUser
    public void postPlanTest() throws Exception {
        doReturn(true).when(service).addPlan(listPlans.get(0));
        doReturn(listPlans.get(0)).when(builder).buildPlan(listPlanDTO.get(0), person);
        String json = jsonMapper.writeValueAsString(listPlanDTO.get(0));
       

        mvc.perform(post("/plans").principal(authenticationToken)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .content(json))
                                 .andDo(print())
                                 .andExpect(status().isOk());
        Mockito.verify(service).addPlan(listPlans.get(0));
        Mockito.verify(builder).buildPlan(listPlanDTO.get(0), person);
    }

    @Test
    @DisplayName("Изменение пункта плана пользователя")
    @WithMockUser
    public void putPlanTest() throws Exception {
        doReturn(true).when(service).editPlan(listPlans.get(0));
        doReturn(listPlans.get(0)).when(builder).buildPlan(listPlanDTO.get(0), person);
        String json = jsonMapper.writeValueAsString(listPlanDTO.get(0));
       

        mvc.perform(put("/plans").principal(authenticationToken)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .content(json))
                                 .andDo(print())
                                 .andExpect(status().isOk());
        Mockito.verify(service).editPlan(listPlans.get(0));
        Mockito.verify(builder).buildPlan(listPlanDTO.get(0), person);
    }

    @Test
    @DisplayName("Удаление пункта плана пользователя")
    @WithMockUser
    public void deletePlanTest() throws Exception {
        doReturn(true).when(service).removePlan(1);

        mvc.perform(delete("/plans/1")).andExpect(status().isOk());
        Mockito.verify(service).removePlan(1);
    }

    @Test
    @DisplayName("Получение суммы планируемого дохода за период")
    @WithMockUser
    public void getIncomePlanTest() throws Exception {
        LocalDate date = LocalDate.of(2024,1,1);
        double result = listPlans.stream().filter(p -> p.getIncome() != null).mapToDouble(p -> p.getSumm()).sum();
        doReturn(result).when(service).getIncomePlan(person, date);

        mvc.perform(get("/plans/income/2024-01-01")).andExpect(status().isOk()).andExpect(value -> value.equals(3000.0));
        Mockito.verify(service).getIncomePlan(person, date);
    }

    @Test
    @DisplayName("Получение суммы планируемого расхода за период")
    @WithMockUser
    public void getExpensesPlanTest() throws Exception {
        LocalDate date = LocalDate.of(2024,1,1);
        double result = listPlans.stream().filter(p -> p.getExpenses() != null).mapToDouble(p -> p.getSumm()).sum();
        doReturn(result).when(service).getExpensesPlan(person, date);

        mvc.perform(get("/plans/expenses/2024-01-01")).andExpect(status().isOk()).andExpect(value -> value.equals(400.0));
        Mockito.verify(service).getExpensesPlan(person, date);
    }

    @Test
    @DisplayName("Получение суммы отложенной на цели за период")
    @WithMockUser
    public void getTargetPlanTest() throws Exception {
        LocalDate date = LocalDate.of(2024,1,1);
        double result = listPlans.stream().filter(p -> p.getTarget() != null).mapToDouble(p -> p.getSumm()).sum();
        doReturn(result).when(service).getTargetPlan(person, date);

        mvc.perform(get("/plans/target/2024-01-01")).andExpect(status().isOk()).andExpect(value -> value.equals(60.0));
        Mockito.verify(service).getTargetPlan(person, date);
    }


    private List<Plan> getPlans() {

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


        List<Target> listTarget = List.of(new Target(1, person, "First Target", 100.0),
                                          new Target(2, person, "Second Target", 200.0),
                                          new Target(3, person, "Next Target", 300.0),
                                          new Target(4, person, "Last Target", 400.0));

        LocalDate date = LocalDate.of(2024, 1, 1);
        List<Plan> result = List.of(new Plan(1, date, person, listIncome.get(0), null, null, 1000.0),
                                    new Plan(2, date, person, listIncome.get(1), null, null, 2000.0),
                                    new Plan(3, date, person, null, listExpenses.get(0), null, 100.0),
                                    new Plan(4, date, person, null, listExpenses.get(1), null, 100.0),
                                    new Plan(5, date, person, null, listExpenses.get(2), null, 100.0),
                                    new Plan(6, date, person, null, listExpenses.get(3), null, 100.0),
                                    new Plan(7, date, person, null, null, listTarget.get(0), 10.0),
                                    new Plan(8, date, person, null, null, listTarget.get(1), 50.0));

        return result;
    }

    private List<PlanDTO> getPlansDTO() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<PlanDTO> result = List.of(new PlanDTO(1, date, 1, 1, 0, 0, 1000.0),
                                       new PlanDTO(2, date, 1, 2, 0, 0, 2000.0),
                                       new PlanDTO(3, date, 1, 0, 1, 0, 100.0),
                                       new PlanDTO(4, date, 1, 0, 2, 0, 100.0),
                                       new PlanDTO(5, date, 1, 0, 3, 0, 100.0),
                                       new PlanDTO(6, date, 1, 0, 4, 0, 100.0),
                                       new PlanDTO(7, date, 1, 0, 0, 1, 10.0),
                                       new PlanDTO(8, date, 1, 0, 0, 2, 50.0));

        return result;
    }
}
