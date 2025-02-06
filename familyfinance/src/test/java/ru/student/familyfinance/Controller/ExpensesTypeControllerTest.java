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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance.Configuration.SecurityConfiguration;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.ExpensesTypeService;

@WebMvcTest(ExpensesTypeController.class)
@Import(SecurityConfiguration.class)
public class ExpensesTypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    
    @MockitoBean
    private ExpensesTypeService service;

    @MockitoBean
    private PersonRepository personRepository;


    @Test
    @DisplayName("Получение списка типов расходов")
    @WithMockUser
    public void getExpensesTypeTest() throws Exception {
        List<ExpensesType> list = List.of(new ExpensesType(1, "First Expenses Type"),
                                          new ExpensesType(2, "Second Expenses Type"),
                                          new ExpensesType(3, "Next Expenses Type"));

        doReturn(list).when(service).getExpensesType();
        
        String json = mapper.writeValueAsString(list);
        
        mvc.perform(get("/expensestypes")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service,Mockito.times(1)).getExpensesType();
    }

    @Test
    @DisplayName("Получение типа расходов по ID")
    @WithMockUser
    public void getExpensesTypeByIdTest() throws Exception {
        ExpensesType expensesType = new ExpensesType(1, "Test Expenses Type");

        doReturn(expensesType).when(service).getExpensesTypeById(1L);

        String json = mapper.writeValueAsString(expensesType);
        mvc.perform(get("/expensestypes/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service,Mockito.times(1)).getExpensesTypeById(1L);
    }

    @Test
    @DisplayName("Добавление нового типа расходов")
    @WithMockUser
    public void postExpensesTypeTest() throws Exception {
        ExpensesType expensesType = new ExpensesType(1, "Test Expenses Type");

        doReturn(expensesType).when(service).addExpensesType(expensesType);

        String json = mapper.writeValueAsString(expensesType);
        mvc.perform(post("/expensestypes").contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.APPLICATION_JSON)
                                          .content(json))
                                          .andDo(print())
                                          .andExpect(status().isOk());
        Mockito.verify(service,Mockito.times(1)).addExpensesType(expensesType);
    }

    @Test
    @DisplayName("Изменение типа расходов")
    @WithMockUser
    public void putExpensesTypeTest() throws Exception {
        ExpensesType expensesType = new ExpensesType(1, "Test Expenses Type");

        doReturn(expensesType).when(service).editExpensesType(expensesType);

        String json = mapper.writeValueAsString(expensesType);
        mvc.perform(put("/expensestypes").contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.APPLICATION_JSON)
                                          .content(json))
                                          .andDo(print())
                                          .andExpect(status().isOk());
        Mockito.verify(service,Mockito.times(1)).editExpensesType(expensesType);
    }

    @Test
    @DisplayName("Удаление типа расходов")
    @WithMockUser
    public void deleteExpensesTypeTest() throws Exception {
        doReturn(true).when(service).removeExpensesType(1);

        mvc.perform(delete("/expensestypes/1")).andExpect(status().isOk());
        Mockito.verify(service,Mockito.times(1)).removeExpensesType(1);
    }

}
