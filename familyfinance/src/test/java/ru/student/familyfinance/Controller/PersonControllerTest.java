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
import ru.student.familyfinance.DTO.PersonDTO;
import ru.student.familyfinance.Mapper.MapperPerson;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.PersonService;

@WebMvcTest(PersonController.class)
@Import(SecurityConfiguration.class)
public class PersonControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person;
    private List<Person> listPersons;
    private List<PersonDTO> listPersonDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private PersonService service;

    @MockitoBean
    private MapperPerson mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        person = new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        listPersons = List.of(new Person(1, "first", "First Person", "1234", "first@server.com", 0, false),
                              new Person(2, "second", "Second Person", "1234", "second@server.com", 0, false),
                              new Person(3, "test", "Test Person", "1234", "test@server.com", 0, false),
                              new Person(4, "admin", "Admin", "1234", "admin@server.com", 1, false));
        listPersonDTO = List.of(new PersonDTO(1, "first", "First Person", "first@server.com", 0, false),
                                new PersonDTO(2, "second", "Second Person", "second@server.com", 0, false),
                                new PersonDTO(3, "test", "Test Person", "test@server.com", 0, false),
                                new PersonDTO(4, "admin", "Admin", "admin@server.com", 1, false));
    }

    @Test
    @DisplayName("Получение списка пользователей")
    @WithMockUser
    public void getPersonsTest() throws Exception {
        doReturn(listPersons).when(service).getPersons();
        doReturn(listPersonDTO).when(mapper).toListPersonDTO(listPersons);
        String json = jsonMapper.writeValueAsString(listPersonDTO);

        mvc.perform(get("/persons")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getPersons();
        Mockito.verify(mapper, Mockito.times(1)).toListPersonDTO(listPersons);
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    @WithMockUser
    public void getPersonByIdTest() throws Exception {
        doReturn(listPersons.get(0)).when(service).getPersonById(1);
        doReturn(listPersonDTO.get(0)).when(mapper).toPersonDTO(listPersons.get(0));
        String json = jsonMapper.writeValueAsString(listPersonDTO.get(0));

        mvc.perform(get("/persons/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getPersonById(1);
        Mockito.verify(mapper, Mockito.times(1)).toPersonDTO(listPersons.get(0));
    }

    @Test
    @DisplayName("Добавление нового пользователя")
    @WithMockUser
    public void postPersonTest() throws Exception {
        doReturn(true).when(service).addPerson(listPersons.get(0));
        doReturn(listPersons.get(0)).when(mapper).toPerson(listPersonDTO.get(0));
        String json = jsonMapper.writeValueAsString(listPersonDTO.get(0));

        mvc.perform(post("/persons").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addPerson(listPersons.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toPerson(listPersonDTO.get(0));
    }

    @Test
    @DisplayName("Изменение пользователя")
    @WithMockUser
    public void putPersonTest() throws Exception {
        doReturn(true).when(service).editPerson(listPersons.get(0));
        doReturn(listPersons.get(0)).when(mapper).toPerson(listPersonDTO.get(0));
        String json = jsonMapper.writeValueAsString(listPersonDTO.get(0));

        mvc.perform(put("/persons").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editPerson(listPersons.get(0));
        Mockito.verify(mapper, Mockito.times(1)).toPerson(listPersonDTO.get(0));
    }

    @Test
    @DisplayName("Удаление пользователя по ID")
    @WithMockUser
    public void deletePersonByIdTest() throws Exception {
        doReturn(true).when(service).removePerson(1);

        mvc.perform(delete("/persons/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removePerson(1);
    }

}
