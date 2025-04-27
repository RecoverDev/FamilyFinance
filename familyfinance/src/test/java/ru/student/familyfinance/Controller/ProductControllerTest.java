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
import ru.student.familyfinance.DTO.ProductDTO;
import ru.student.familyfinance.Mapper.MapperProduct;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;
import ru.student.familyfinance.Repository.PersonRepository;
import ru.student.familyfinance.Service.ProductService;

@WebMvcTest(ProductController.class)
@Import(SecurityConfiguration.class)
public class ProductControllerTest {
    private UsernamePasswordAuthenticationToken authenticationToken;
    private Person person = getPerson();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockitoBean
    private ProductService service;

    @MockitoBean
    private MapperProduct mapper;

    @MockitoBean
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        authenticationToken = new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    @DisplayName("Получение списка товаров пользователя")
    @WithMockUser
    public void getProductsTest() throws Exception {
        doReturn(getProducts()).when(service).getProduct(person);
        doReturn(getProductsDTO()).when(mapper).toListProductDTO(getProducts());
        String json = jsonMapper.writeValueAsString(getProductsDTO());

        mvc.perform(get("/products")).andExpect(status().isOk()).andExpect(content().json(json));

        Mockito.verify(service, Mockito.times(1)).getProduct(person);
        Mockito.verify(mapper, Mockito.times(1)).toListProductDTO(getProducts());
    }

    @Test
    @DisplayName("Получение товара пользователя по ID")
    @WithMockUser
    public void getProductByIdTest() throws Exception {
        doReturn(getProducts().get(0)).when(service).getProductById(1);
        doReturn(getProductsDTO().get(0)).when(mapper).toProductDTO(getProducts().get(0));
        String json = jsonMapper.writeValueAsString(getProductsDTO().get(0));

        mvc.perform(get("/products/1")).andExpect(status().isOk()).andExpect(content().json(json));
        Mockito.verify(service, Mockito.times(1)).getProductById(1);
        Mockito.verify(mapper, Mockito.times(1)).toProductDTO(getProducts().get(0));
    }

    @Test
    @DisplayName("Добавление нового товара пользователя")
    @WithMockUser
    public void postTargetTest() throws Exception {
        doReturn(getProducts().get(0)).when(mapper).toProduct(getProductsDTO().get(0));
        doReturn(getProductsDTO().get(0)).when(mapper).toProductDTO(getProducts().get(0));
        doReturn(getProducts().get(0)).when(service).addProduct(getProducts().get(0));
        String json = jsonMapper.writeValueAsString(getProductsDTO().get(0));

        mvc.perform(post("/products").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).addProduct(getProducts().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toProduct(getProductsDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toProductDTO(getProducts().get(0));
    }

    @Test
    @DisplayName("Изменение товара пользователя")
    @WithMockUser
    public void putProductTest() throws Exception {
        doReturn(getProducts().get(0)).when(mapper).toProduct(getProductsDTO().get(0));
        doReturn(getProductsDTO().get(0)).when(mapper).toProductDTO(getProducts().get(0));
        doReturn(getProducts().get(0)).when(service).editProduct(getProducts().get(0));
        String json = jsonMapper.writeValueAsString(getProductsDTO().get(0));

        mvc.perform(put("/products").principal(authenticationToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(json))
                                    .andDo(print())
                                    .andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).editProduct(getProducts().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toProduct(getProductsDTO().get(0));
        Mockito.verify(mapper, Mockito.times(1)).toProductDTO(getProducts().get(0));
    }

    @Test
    @DisplayName("Удаление товара пользователя по ID")
    @WithMockUser
    public void deleteTargetTest() throws Exception {
        doReturn(true).when(service).removeProduct(1);

        mvc.perform(delete("/products/1")).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).removeProduct(1);
    }

    private Person getPerson() {
        return new Person(1, "test", "TestUser", "111", "test@server.com", 0, false);
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

    private List<ProductDTO> getProductsDTO() {
        return List.of(new ProductDTO(1, 1, "Product 1", 1),
                       new ProductDTO(2, 1, "Product 2", 1),
                       new ProductDTO(3, 1, "Product 3", 1),
                       new ProductDTO(4, 1, "Product 4", 2),
                       new ProductDTO(5, 1, "Product 5", 2),
                       new ProductDTO(6, 1, "Product 6", 2),
                       new ProductDTO(7, 1, "Product 7", 3),
                       new ProductDTO(8, 1, "Product 8", 3),
                       new ProductDTO(9, 1, "Product 1", 3),
                       new ProductDTO(10, 1, "Product 1", 4),
                       new ProductDTO(11, 1, "Product 1", 4),
                       new ProductDTO(12, 1, "Product 1", 4),
                       new ProductDTO(13, 1, "Product 1", 5));
    }
}
