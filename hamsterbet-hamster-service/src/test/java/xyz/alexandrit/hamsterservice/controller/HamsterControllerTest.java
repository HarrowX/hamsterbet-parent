package xyz.alexandrit.hamsterservice.controller;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import xyz.alexandrit.hamsterservice.entity.Hamster;
import xyz.alexandrit.hamsterservice.repository.HamsterRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class HamsterControllerTest {
    @Setter(onMethod_ = @Autowired)
    private MockMvc mockMvc;

    @Setter(onMethod_ = @Autowired)
    private HamsterRepository hamsterRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    Map<String, Long> hamsterIds = new HashMap<>();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    @Transactional
    void setUp() {
        hamsterRepository.deleteAll();

        Hamster hamster1 = new Hamster();
        hamster1.setName("anton");
        hamster1.setWeight(126.0);
        hamster1.setStrength(2);
        hamster1.setAge(1);

        Hamster hamster2 = new Hamster();
        hamster2.setName("mark");
        hamster2.setWeight(176.5);
        hamster2.setStrength(4);
        hamster2.setAge(3);

        hamsterRepository.saveAll(List.of(hamster1, hamster2)).forEach(hamster -> hamsterIds.put(hamster.getName(), hamster.getId()));
    }

    @Test
    void findAllHamsters_shouldReturnHamstersList() throws Exception {
        mockMvc.perform(get("/api/v1/hamsters"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].hamsterId").value(hamsterIds.get("anton")),
                        jsonPath("$[0].name").value("anton"),
                        jsonPath("$[0].weight").value(126.0),
                        jsonPath("$[0].strength").value(2),
                        jsonPath("$[0].age").value(1),
                        jsonPath("$[1].hamsterId").value(hamsterIds.get("mark")),
                        jsonPath("$[1].name").value("mark"),
                        jsonPath("$[1].weight").value(176.5),
                        jsonPath("$[1].strength").value(4),
                        jsonPath("$[1].age").value(3)
                );
    }

    @Test
    void findById_shouldReturnHamster() throws Exception {
        mockMvc.perform(get("/api/v1/hamsters/" + hamsterIds.get("anton")))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.hamsterId").value(hamsterIds.get("anton")),
                        jsonPath("$.name").value("anton"),
                        jsonPath("$.weight").value(126.0),
                        jsonPath("$.strength").value(2),
                        jsonPath("$.age").value(1)
                );
    }

    @Test
    void findById_shouldReturnNotFoundForNonExistingHamster() throws Exception {
        long nonExistingId = 999L;
        mockMvc.perform(get("/api/v1/hamsters/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void registerHamster_shouldCreateNewHamster() throws Exception {
        String newHamsterJson = """
                {
                    "name": "newbie",
                    "weight": 150.0,
                    "strength": 3,
                    "age": 2
                }
                """;

        mockMvc.perform(post("/api/v1/hamsters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newHamsterJson))
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.hamsterId").exists()
                );

        // Verify the hamster was actually created
        mockMvc.perform(get("/api/v1/hamsters"))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void registerHamster_shouldReturnBadRequestForInvalidData() throws Exception {
        String invalidHamsterJson = """
                {
                    "name": "",
                    "weight": -1.0,
                    "strength": 0,
                    "age": 11
                }
                """;

        mockMvc.perform(post("/api/v1/hamsters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidHamsterJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateHamster_shouldUpdateExistingHamster() throws Exception {
        String updateHamsterJson = """
                {
                    "name": "updated anton",
                    "weight": 130.0,
                    "strength": 5,
                    "age": 2
                }
                """;

        mockMvc.perform(put("/api/v1/hamsters/" + hamsterIds.get("anton"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateHamsterJson))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.hamsterId").value(hamsterIds.get("anton")),
                        jsonPath("$.name").value("updated anton"),
                        jsonPath("$.weight").value(130.0),
                        jsonPath("$.strength").value(5),
                        jsonPath("$.age").value(2)
                );

        // Verify the hamster was actually updated
        mockMvc.perform(get("/api/v1/hamsters/" + hamsterIds.get("anton")))
                .andExpect(jsonPath("$.name").value("updated anton"));
    }

    @Test
    void updateHamster_shouldReturnNotFoundForNonExistingHamster() throws Exception {
        long nonExistingId = 999L;
        String updateHamsterJson = """
                {
                    "name": "non-existent",
                    "weight": 100.0,
                    "strength": 1,
                    "age": 1
                }
                """;

        mockMvc.perform(put("/api/v1/hamsters/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateHamsterJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateHamster_shouldReturnBadRequestForInvalidData() throws Exception {
        String invalidHamsterJson = """
                {
                    "name": "",
                    "weight": -1.0,
                    "strength": 0,
                    "age": 11
                }
                """;

        mockMvc.perform(put("/api/v1/hamsters/" + hamsterIds.get("anton"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidHamsterJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteHamster_shouldDeleteExistingHamster() throws Exception {
        mockMvc.perform(delete("/api/v1/hamsters/" + hamsterIds.get("anton")))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.hamsterId").value(hamsterIds.get("anton"))
                );

        // Verify the hamster is actually deleted
        mockMvc.perform(get("/api/v1/hamsters/" + hamsterIds.get("anton")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteHamster_shouldReturnOkForNonExistingHamster() throws Exception {
        long nonExistingId = 999L;
        mockMvc.perform(delete("/api/v1/hamsters/" + nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }
}