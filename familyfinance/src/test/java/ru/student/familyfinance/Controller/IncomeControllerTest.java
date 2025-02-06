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
import ru.student.familyfinance.DTO.IncomeDTO;
import ru.student.familyfinance.Mapper.MapperIncome;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.IncomeService;

@WebMvcTest(IncomeController.class)
@Import(SecurityConfiguration.class)
public class IncomeControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<Income> listIncome;
    private List<IncomeDTO> listIncomeDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private IncomeService service;

    @MockitoBean
    private MapperIncome mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        listIncome = List.of(new Income(1, person, "First Income"),
                             new Income(2, person, "Second Income"),
                             new Income(3, person, "Next Income"),
                             new Income(4, person, "Last Income"));
        listIncomeDTO = List.of(new IncomeDTO(1, 1, "First Income"),
                                new IncomeDTO(2, 1, "Second Income"),
                                new IncomeDTO(3, 1, "Next Income"),
                                new IncomeDTO(4, 1, "Last Income"));
    }

    @Test
    @DisplayName("Получение списка категорий дохода")
    @WithMockUser
    public void getIncomesTest() throws Exception {
        doReturn(listIncome).when(service).getIncomes(person);
        doReturn(listIncomeDTO).when(mapper).toListIncomeDTO(listIncome);
        String json = jsonMapper.writeValueAsString(listIncomeDTO);

        mvc.perform(get("/incomes").principal(authenticationToken)).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getIncomes(person);
        Mockito.verify(mapper, Mockito.times(1)).toListIncomeDTO(listIncome);
    }

    @Test
    @DisplayName("Получение категории доходов по ID")
    @WithMockUser
    public void getIncomeByIdTest() throws Exception {
        doReturn(listIncome.get(0)).when(service).getIncomeById(1);
        doReturn(listIncomeDTO.get(0)).when(mapper).toIncomeDTO(listIncome.get(0));
        String json = jsonMapper.writeValueAsString(listIncomeDTO.get(0));

        mvc.perform(get("/incomes/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getIncomeById(1);
        Mockito.verify(mapper, Mockito.times(1)).toIncomeDTO(listIncome.get(0));
    }

    @Test
    @DisplayName("Добавление новой категории доходов")
    @WithMockUser
    public void postIncomeTest() throws Exception {
        doReturn(listIncome.get(0)).when(mapper).toIncome(listIncomeDTO.get(0));
        doReturn(listIncomeDTO.get(0)).when(mapper).toIncomeDTO(listIncome.get(0));
        doReturn(listIncome.get(0)).when(service).addIncome(listIncome.get(0));
        String json = jsonMapper.writeValueAsString(listIncomeDTO.get(0));

        mvc.perform(post("/incomes").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addIncome(listIncome.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toIncome(listIncomeDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toIncomeDTO(listIncome.get(0));
    }

    @Test
    @DisplayName("Изменение категории доходов")
    @WithMockUser
    public void putIncomeTest() throws Exception {
        doReturn(listIncome.get(0)).when(mapper).toIncome(listIncomeDTO.get(0));
        doReturn(listIncomeDTO.get(0)).when(mapper).toIncomeDTO(listIncome.get(0));
        doReturn(listIncome.get(0)).when(service).editIncome(listIncome.get(0));
        String json = jsonMapper.writeValueAsString(listIncomeDTO.get(0));

        mvc.perform(put("/incomes").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editIncome(listIncome.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toIncome(listIncomeDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toIncomeDTO(listIncome.get(0));
                                }

    @Test
    @DisplayName("Удаление категории доходов по ID")
    @WithMockUser
    public void deleteIncomeTest() throws Exception {
        doReturn(true).when(service).removeIncome(1);

        mvc.perform(delete("/incomes/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeIncome(1);
    }
}
