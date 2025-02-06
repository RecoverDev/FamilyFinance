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
import ru.student.familyfinance.DTO.TargetDTO;
import ru.student.familyfinance.Mapper.MapperTarget;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.TargetService;

@WebMvcTest(TargetController.class)
@Import(SecurityConfiguration.class)
public class TargetControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<Target> listTarget;
    private List<TargetDTO> listTargetDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private TargetService service;

    @MockitoBean
    private MapperTarget mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        listTarget = List.of(new Target(1, person, "First Target", 100.0, LocalDate.of(2024, 10, 1)),
                             new Target(2, person, "Second Target", 200.0, LocalDate.of(2024, 10, 1)),
                             new Target(3, person, "Next Target", 300.0, LocalDate.of(2024, 10, 1)),
                             new Target(4, person, "Last Target", 400.0, LocalDate.of(2024, 10, 1)));
        listTargetDTO = List.of(new TargetDTO(1, 1, "First Target", 100.0, LocalDate.of(2024, 10, 1)),
                                new TargetDTO(2, 1, "Second Target", 200.0, LocalDate.of(2024, 10, 1)),
                                new TargetDTO(3, 1, "Next Target", 300.0, LocalDate.of(2024, 10, 1)),
                                new TargetDTO(4, 1, "Last Target", 400.0, LocalDate.of(2024, 10, 1)));
    }

    @Test
    @DisplayName("Получение списка целей пользователя")
    @WithMockUser
    public void getTargetsTest() throws Exception {
        doReturn(listTarget).when(service).getTarget(person);
        doReturn(listTargetDTO).when(mapper).toListTargetDTO(listTarget);
        String json = jsonMapper.writeValueAsString(listTargetDTO);

        mvc.perform(get("/targets")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getTarget(person);
        Mockito.verify(mapper, Mockito.times(1)).toListTargetDTO(listTarget);
    }

    @Test
    @DisplayName("Получение цели по ID")
    @WithMockUser
    public void getTargetByIdTest() throws Exception {
        doReturn(listTarget.get(0)).when(service).getTargetById(1);
        doReturn(listTargetDTO.get(0)).when(mapper).toTargetDTO(listTarget.get(0));
        String json = jsonMapper.writeValueAsString(listTargetDTO.get(0));

        mvc.perform(get("/targets/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getTargetById(1);
        Mockito.verify(mapper, Mockito.times(1)).toTargetDTO(listTarget.get(0));
    }

    @Test
    @DisplayName("Добавление новой цели пользователя")
    @WithMockUser
    public void postTargetTest() throws Exception {
        doReturn(listTarget.get(0)).when(mapper).toTarget(listTargetDTO.get(0));
        doReturn(listTargetDTO.get(0)).when(mapper).toTargetDTO(listTarget.get(0));
        doReturn(listTarget.get(0)).when(service).addTarget(listTarget.get(0));
        String json = jsonMapper.writeValueAsString(listTargetDTO.get(0));

        mvc.perform(post("/targets").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addTarget(listTarget.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toTarget(listTargetDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toTargetDTO(listTarget.get(0));
    }

    @Test
    @DisplayName("Изменение цели пользователя")
    @WithMockUser
    public void putTargetTest() throws Exception {
        doReturn(listTarget.get(0)).when(mapper).toTarget(listTargetDTO.get(0));
        doReturn(listTargetDTO.get(0)).when(mapper).toTargetDTO(listTarget.get(0));
        doReturn(listTarget.get(0)).when(service).editTarget(listTarget.get(0));
        String json = jsonMapper.writeValueAsString(listTargetDTO.get(0));

        mvc.perform(put("/targets").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editTarget(listTarget.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toTarget(listTargetDTO.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toTargetDTO(listTarget.get(0));
    }

    @Test
    @DisplayName("Удаление цули пользователя по ID")
    @WithMockUser
    public void deleteTargetTest() throws Exception {
        doReturn(true).when(service).removeTarget(1);

        mvc.perform(delete("/targets/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeTarget(1);
    }
}
