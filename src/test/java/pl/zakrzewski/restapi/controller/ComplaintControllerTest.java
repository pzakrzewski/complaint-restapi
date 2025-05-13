package pl.zakrzewski.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zakrzewski.restapi.AbstractIntegrationTest;
import pl.zakrzewski.restapi.config.TestSecurityConfig;
import pl.zakrzewski.restapi.model.Complaint;
import pl.zakrzewski.restapi.model.Product;
import pl.zakrzewski.restapi.model.User;
import pl.zakrzewski.restapi.model.dto.ComplaintDto;
import pl.zakrzewski.restapi.model.dto.ComplaintPostCommand;
import pl.zakrzewski.restapi.model.dto.ComplaintPutCommand;
import pl.zakrzewski.restapi.repository.ComplaintRepository;
import pl.zakrzewski.restapi.repository.ProductRepository;
import pl.zakrzewski.restapi.repository.UserRepository;
import pl.zakrzewski.restapi.service.GeoLocationService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class ComplaintControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @MockitoBean
    private GeoLocationService geoLocationService;

    @BeforeEach
    void init() {
        Mockito.when(geoLocationService.getCountryByIp(Mockito.any())).thenReturn("Poland");
    }

    @Test
    @Transactional
    void shouldGetSingleComplaint() throws Exception {
        // given
        Product product = new Product();
        product.setName("Test product");

        User user = new User();
        user.setFirstName("Adrian");
        user.setLastName("W");

        Complaint newComplaint = Complaint.builder()
                .product(product)
                .user(user)
                .country("Poland")
                .content("Test content")
                .created(LocalDateTime.now())
                .build();

        userRepository.save(user);
        productRepository.save(product);
        complaintRepository.save(newComplaint);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/complaints/" + newComplaint.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Complaint complaint = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Complaint.class);
        assertThat(complaint).isNotNull();
        assertThat(complaint.getId()).isEqualTo(newComplaint.getId());
        assertThat(complaint.getCountry()).isEqualTo("Poland");
        assertThat(complaint.getContent()).isEqualTo("Test content");
    }

    @Test
    @Transactional
    void shouldThrowExceptionWhenGetSingleComplaint() throws Exception {
        // given

        // when
        MvcResult mvcResult = mockMvc.perform(get("/complaints/" + "1"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Complaint not found with id: 1");
    }

    @Test
    @Transactional
    void shouldDeleteSingleComplaint() throws Exception {
        // given
        Product product = new Product();
        product.setName("Test product");

        User user = new User();
        user.setFirstName("Adrian");
        user.setLastName("W");

        Complaint newComplaint = Complaint.builder()
                .product(product)
                .user(user)
                .country("Poland")
                .content("Test content")
                .created(LocalDateTime.now())
                .build();

        userRepository.save(user);
        productRepository.save(product);
        complaintRepository.save(newComplaint);

        // when
        MvcResult mvcResult = mockMvc.perform(delete("/complaints/" + newComplaint.getId()))
                .andDo(print())
                .andExpect(status().is(204))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(204);
    }

    @Test
    @Transactional
    void shouldThrowExceptionWhenDeleteSingleComplaint() throws Exception {
        // given

        // when
        MvcResult mvcResult = mockMvc.perform(delete("/complaints/" + "1"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Complaint not found with id: 1");
    }

    @Test
    @Transactional
    void shouldEditSingleComplaint() throws Exception {
        // given
        Product product = new Product();
        product.setName("Test product");

        User user = new User();
        user.setFirstName("Adrian");
        user.setLastName("W");

        Complaint newComplaint = Complaint.builder()
                .product(product)
                .user(user)
                .country("Poland")
                .content("Test content")
                .created(LocalDateTime.now())
                .build();

        userRepository.save(user);
        productRepository.save(product);
        complaintRepository.save(newComplaint);

        ComplaintPutCommand complaintPutCommand = ComplaintPutCommand.builder().content("Changed content").build();

        // when
        MvcResult mvcResult = mockMvc.perform(put("/complaints/" + newComplaint.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintPutCommand)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        ComplaintDto complaint = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ComplaintDto.class);
        assertThat(complaint).isNotNull();
        assertThat(complaint.getId()).isEqualTo(newComplaint.getId());
        assertThat(complaint.getCountry()).isEqualTo("Poland");
        assertThat(complaint.getContent()).isEqualTo("Changed content");

    }

    @Test
    @Transactional
    void shouldThrowExceptionWhenEditSingleComplaint() throws Exception {
        // given

        ComplaintPutCommand complaintPutCommand = ComplaintPutCommand.builder().content("Changed content").build();

        // when
        MvcResult mvcResult = mockMvc.perform(put("/complaints/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintPutCommand)))
                .andExpect(status().is(404))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Complaint not found with id: 1");
    }

    @Test
    @Transactional
    void shouldAddSingleComplaint() throws Exception {
        // given
        Product product = new Product();
        product.setName("Test product");

        User user = new User();
        user.setFirstName("Adrian");
        user.setLastName("W");

        userRepository.save(user);
        productRepository.save(product);

        ComplaintPostCommand complaintPostCommand = ComplaintPostCommand.builder()
                .productId(product.getId())
                .userId(user.getId())
                .content("Test content")
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintPostCommand)))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        ComplaintDto complaint = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ComplaintDto.class);
        assertThat(complaint).isNotNull();
        assertThat(complaint.getCountry()).isEqualTo("Poland");
        assertThat(complaint.getContent()).isEqualTo("Test content");
    }

    @Test
    @Transactional
    void shouldThrowProductExceptionWhenAddSingleComplaint() throws Exception {
        // given
        User user = new User();
        user.setFirstName("Adrian");
        user.setLastName("W");

        userRepository.save(user);

        ComplaintPostCommand complaintPostCommand = ComplaintPostCommand.builder()
                .productId(10L)
                .userId(user.getId())
                .content("Test content")
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintPostCommand)))
                .andExpect(status().is(404))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Product not found with id: 10");
    }

    @Test
    @Transactional
    void shouldThrowUserExceptionWhenAddSingleComplaint() throws Exception {
        // given
        Product product = new Product();
        product.setName("Test product");

        productRepository.save(product);

        ComplaintPostCommand complaintPostCommand = ComplaintPostCommand.builder()
                .productId(product.getId())
                .userId(10L)
                .content("Test content")
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(complaintPostCommand)))
                .andExpect(status().is(404))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("User not found with id: 10");
    }

}
