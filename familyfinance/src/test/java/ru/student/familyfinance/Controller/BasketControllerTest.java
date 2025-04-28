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
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.student.familyfinance.Configuration.SecurityConfiguration;
import ru.student.familyfinance.DTO.BasketDTO;
import ru.student.familyfinance.Mapper.MapperBasket;
import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Model.Shop;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.BasketService;
import ru.student.familyfinance.Service.ShopService;

@WebMvcTest(BasketController.class)
@Import(SecurityConfiguration.class)
public class BasketControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person = getPerson();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private BasketService service;

    @MockitoBean
    private ShopService shopService;

    @MockitoBean
    private MapperBasket mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    @DisplayName("Получение списка покупок пользователя")
    @WithMockUser
    public void getBasketTest() throws Exception {
        doReturn(getBaskets()).when(service).getBasket(person);
        doReturn(getBasketDTO()).when(mapper).toListBasketDTO(getBaskets());
        String json = jsonMapper.writeValueAsString(getBasketDTO());

        mvc.perform(get("/baskets")).andExpect(status().isOk()).andExpect(content().json(json));

        Mockito.verify(service, Mockito.times(1)).getBasket(person);
        Mockito.verify(mapper, Mockito.times(1)).toListBasketDTO(getBaskets());
    }

    @Test
    @DisplayName("Получение покупки пользователя по ID")
    @WithMockUser
    public void getBasketByIdTest() throws Exception {
        doReturn(getProducts().get(0)).when(service).getBasketById(1L);
        doReturn(getBasketDTO().get(0)).when(mapper).toBasketDTO(getBaskets().get(0));
        String json = jsonMapper.writeValueAsString(getBasketDTO().get(0));

        mvc.perform(get("/baskets/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getBasketById(1L);
        Mockito.verify(mapper, Mockito.times(1)).toBasketDTO(getBaskets().get(0));
    }

    @Test
    @DisplayName("Добавление новой покупки пользователя")
    @WithMockUser
    public void postTargetTest() throws Exception {
        doReturn(getBaskets().get(0)).when(mapper).toBasket(getBasketDTO().get(0));
        doReturn(getBasketDTO().get(0)).when(mapper).toBasketDTO(getBaskets().get(0));
        doReturn(getBaskets().get(0)).when(service).addBasket(getBaskets().get(0));
        String json = jsonMapper.writeValueAsString(getBasketDTO().get(0));

        mvc.perform(post("/baskets").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addBasket(getBaskets().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toBasketDTO(getBaskets().get(0));
    }

    @Test
    @DisplayName("Изменение покупки пользователя")
    @WithMockUser
    public void putProductTest() throws Exception {
        doReturn(getBaskets().get(0)).when(mapper).toBasket(getBasketDTO().get(0));
        doReturn(getBasketDTO().get(0)).when(mapper).toBasketDTO(getBaskets().get(0));
        doReturn(getBaskets().get(0)).when(service).editBasket(getBaskets().get(0));
        String json = jsonMapper.writeValueAsString(getBasketDTO().get(0));

        mvc.perform(put("/baskets").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editBasket(getBaskets().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toBasketDTO(getBaskets().get(0));
    }

    @Test
    @DisplayName("Удаление покупки пользователя по ID")
    @WithMockUser
    public void deleteTargetTest() throws Exception {
        doReturn(true).when(service).removeBasket(1);

        mvc.perform(delete("/baskets/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeBasket(1);
    }

    @Test
    @DisplayName("Формирование списка товаров по ID магазина")
    @WithMockUser
    public void getBasketByShopTest() throws Exception {
        doReturn(getShops().get(0)).when(shopService).getShopById(1L);
        List<Basket> baskets = getBaskets().stream().filter(s -> s.getShop().equals(getShops().get(0))).toList();
        doReturn(baskets).when(service).getBasketByShop(person, getShops().get(0));
        List<BasketDTO> basketsDTO = getBasketDTO().stream().filter(b -> b.getShop_id() == 1L).toList();
        doReturn(basketsDTO).when(mapper).toListBasketDTO(baskets);
        String json = jsonMapper.writeValueAsString(basketsDTO);

        mvc.perform(get("/baskets/shop/1")).andExpect(status().isOk()).andExpect(content().json(json));

        Mockito.verify(service, Mockito.times(1)).getBasketByShop(person, getShops().get(0));
        Mockito.verify(shopService, Mockito.times(1)).getShopById(1L);
        Mockito.verify(mapper, Mockito.times(1)).toListBasketDTO(baskets);
    }

    @Test
    @DisplayName("Формирование расходных операций из покупок пользователя")
    @WithMockUser
    public void createPurchaseTest() throws Exception {
        List<Pair<BasketDTO,Double>> list = List.of(Pair.of(getBasketDTO().get(0), 100.0),
                                                    Pair.of(getBasketDTO().get(1), 200.0),
                                                    Pair.of(getBasketDTO().get(2), 300.0),
                                                    Pair.of(getBasketDTO().get(3), 400.0));
        List<Pair<Basket, Double>> purchases = List.of(Pair.of(getBaskets().get(0), 100.0),
                                                       Pair.of(getBaskets().get(1), 200.0),
                                                       Pair.of(getBaskets().get(2), 300.0),
                                                       Pair.of(getBaskets().get(3), 400.0));

        doReturn(getBaskets().get(0)).when(mapper).toBasket(getBasketDTO().get(0));
        doReturn(getBaskets().get(1)).when(mapper).toBasket(getBasketDTO().get(1));
        doReturn(getBaskets().get(2)).when(mapper).toBasket(getBasketDTO().get(2));
        doReturn(getBaskets().get(3)).when(mapper).toBasket(getBasketDTO().get(3));
        doReturn(true).when(service).makePurchase(person, purchases);
        String json = jsonMapper.writeValueAsString(list);

        mvc.perform(post("/baskets/purchase").principal(authenticationToken)
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .content(json))
                                             .andDo(print())
                                             .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).makePurchase(person, purchases);
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(1));
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(2));
        Mockito.verify(mapper, Mockito.times(1)).toBasket(getBasketDTO().get(3));
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

    private List<Expenses> getExpenses() {
        List<ExpensesType> types = List.of(new ExpensesType(1, "Type 1"),
                                           new ExpensesType(2, "Type 2"),
                                           new ExpensesType(3, "Type 3"),
                                           new ExpensesType(4, "Type 4"));
        return List.of(new Expenses(1, person, "Expenses 1", types.get(0)),
                       new Expenses(2, person, "Expenses 2", types.get(0)),
                       new Expenses(3, person, "Expenses 3", types.get(1)),
                       new Expenses(4, person, "Expenses 4", types.get(1)),
                       new Expenses(5, person, "Expenses 5", types.get(2)),
                       new Expenses(6, person, "Expenses 6", types.get(2)),
                       new Expenses(7, person, "Expenses 7", types.get(3)),
                       new Expenses(8, person, "Expenses 8", types.get(3)));
    }

    private List<Product> getProducts() {
        return List.of(new Product(1, "Product 1", person, getExpenses().get(0)),
                       new Product(2, "Product 2", person, getExpenses().get(0)),
                       new Product(3, "Product 3", person, getExpenses().get(0)),
                       new Product(4, "Product 4", person, getExpenses().get(1)),
                       new Product(5, "Product 5", person, getExpenses().get(1)),
                       new Product(6, "Product 6", person, getExpenses().get(1)),
                       new Product(7, "Product 7", person, getExpenses().get(2)),
                       new Product(8, "Product 8", person, getExpenses().get(2)),
                       new Product(9, "Product 9", person, getExpenses().get(2)),
                       new Product(10, "Product 10", person, getExpenses().get(3)),
                       new Product(11, "Product 11", person, getExpenses().get(3)),
                       new Product(12, "Product 12", person, getExpenses().get(3)),
                       new Product(13, "Product 13", person, getExpenses().get(4)));
    }

    private List<Basket> getBaskets() {
        return List.of(new Basket(1, person, getShops().get(0), getProducts().get(0)),
                       new Basket(2, person, getShops().get(0), getProducts().get(1)),
                       new Basket(3, person, getShops().get(0), getProducts().get(2)),
                       new Basket(4, person, getShops().get(0), getProducts().get(3)),
                       new Basket(5, person, getShops().get(1), getProducts().get(4)),
                       new Basket(6, person, getShops().get(1), getProducts().get(5)),
                       new Basket(7, person, getShops().get(1), getProducts().get(6)),
                       new Basket(8, person, getShops().get(2), getProducts().get(7)),
                       new Basket(9, person, getShops().get(2), getProducts().get(8)),
                       new Basket(10, person, getShops().get(2), getProducts().get(9)),
                       new Basket(11, person, getShops().get(3), getProducts().get(10)),
                       new Basket(12, person, getShops().get(3), getProducts().get(11)),
                       new Basket(13, person, getShops().get(3), getProducts().get(12)));
    }

    private List<BasketDTO> getBasketDTO() {
        return List.of(new BasketDTO(1, 1, 1, 1),
                    new BasketDTO(2, 1, 2, 1),
                    new BasketDTO(3, 1, 3, 1),
                    new BasketDTO(4, 1, 4, 1),
                    new BasketDTO(5, 1, 5, 2),
                    new BasketDTO(6, 1, 6, 2),
                    new BasketDTO(7, 1, 7, 2),
                    new BasketDTO(8, 1, 8, 3),
                    new BasketDTO(9, 1, 9, 3),
                    new BasketDTO(10, 1, 10, 3),
                    new BasketDTO(11, 1, 11, 4),
                    new BasketDTO(12, 1, 12, 4),
                    new BasketDTO(13, 1, 13, 4));
    }
}