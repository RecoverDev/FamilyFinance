package ru.student.familyfinance.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;

import ru.student.familyfinance.Configuration.SecurityConfiguration;
import ru.student.familyfinance.Repository.PersonRepository;

@WebMvcTest(TestController.class)
@Import(SecurityConfiguration.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PersonRepository personRepository;


    @Test
    @DisplayName("Тестирование контроллера TestController")
    @WithAnonymousUser
    public void getTestTest() throws Exception {
        mvc.perform(get("/test")).andExpect(status().isOk()).andExpect(content().string("TEST"));
    }

}
