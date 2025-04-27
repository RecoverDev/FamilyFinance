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
import ru.student.familyfinance.DTO.ShopDTO;
import ru.student.familyfinance.Mapper.MapperShop;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.ShopService;

@WebMvcTest(ShopController.class)
@Import(SecurityConfiguration.class)
public class ShopControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person = getPerson();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private ShopService service;

    @MockitoBean
    private MapperShop mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    @DisplayName("Получение списка магазинов")
    @WithMockUser
    public void getShopsTest() throws Exception {
        doReturn(getShops()).when(service).getShops(person);
        doReturn(getShopDTO()).when(mapper).toListShopDTO(getShops());
        String json = jsonMapper.writeValueAsString(getShopDTO());

        mvc.perform(get("/shops")).andExpect(status().isOk()).andExpect(content().json(json));

        Mockito.verify(service, Mockito.times(1)).getShops(person);
        Mockito.verify(mapper, Mockito.times(1)).toListShopDTO(getShops());
    }

    @Test
    @DisplayName("Получение магазина пользователя по ID")
    @WithMockUser
    public void getShopByIdTest() throws Exception {
        doReturn(getShops().get(0)).when(service).getShopById(1L);
        doReturn(getShopDTO().get(0)).when(mapper).toShopDTO(getShops().get(0));
        String json = jsonMapper.writeValueAsString(getShopDTO().get(0));

        mvc.perform(get("/shops/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getShopById(1L);
        Mockito.verify(mapper, Mockito.times(1)).toShopDTO(getShops().get(0));
    }

    @Test
    @DisplayName("Добавление нового магазина пользователя")
    @WithMockUser
    public void postShopTest() throws Exception {
        doReturn(getShops().get(0)).when(mapper).toShop(getShopDTO().get(0));
        doReturn(getShopDTO().get(0)).when(mapper).toShopDTO(getShops().get(0));
        doReturn(getShops().get(0)).when(service).addShop(getShops().get(0));
        String json = jsonMapper.writeValueAsString(getShopDTO().get(0));

        mvc.perform(post("/shops").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addShop(getShops().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toShop(getShopDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toShopDTO(getShops().get(0));
    }

    @Test
    @DisplayName("Изменение магазина пользователя")
    @WithMockUser
    public void putShopTest() throws Exception {
        doReturn(getShops().get(0)).when(mapper).toShop(getShopDTO().get(0));
        doReturn(getShopDTO().get(0)).when(mapper).toShopDTO(getShops().get(0));
        doReturn(getShops().get(0)).when(service).editShop(getShops().get(0));
        String json = jsonMapper.writeValueAsString(getShopDTO().get(0));

        mvc.perform(put("/shops").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editShop(getShops().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toShop(getShopDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toShopDTO(getShops().get(0));
    }

    @Test
    @DisplayName("Удаление магазина пользователя по ID")
    @WithMockUser
    public void deleteTargetTest() throws Exception {
        doReturn(true).when(service).removeShop(1);

        mvc.perform(delete("/shops/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeShop(1);
    }

    private Person getPerson() {
        return new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
    }

    private List<Shop> getShops() {
        return List.of(new Shop(1, "Shop 1", person),
                       new Shop(2, "Shop 2", person),
                       new Shop(3, "Shop 3", person),
                       new Shop(4, "Shop 4", person));
    }

    private List<ShopDTO> getShopDTO() {
        return List.of(new ShopDTO(1, 1, "Shop 1"),
                       new ShopDTO(2, 1, "Shop 2"),
                       new ShopDTO(3, 1, "Shop 3"),
                       new ShopDTO(4, 1, "Shop 4"));
    }
}
