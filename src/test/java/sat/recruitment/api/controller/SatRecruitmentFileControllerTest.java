package sat.recruitment.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sat.recruitment.api.Datos;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.service.SatRecruitmentFileService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SatRecruitmentFileController.class)
class SatRecruitmentFileControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SatRecruitmentFileService satRecruitmentFileService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testCreateUSer() {
        UserDTO user = new UserDTO(1L, "Sergio", "sergio.crocetta@gmail.com", "Avenida Rivadavia 77",
                "15151515111", "Normal", Double.valueOf(1000));
        when(satRecruitmentFileService.createUser(any())).then(invocation -> {
            UserDTO u = invocation.getArgument(0);
            u.setId(3L);
            return u;
        });

        try {
            mvc.perform(post("/api/v1/create-user").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(3)))
                    .andExpect(jsonPath("$.name", is("Sergio")))
                    .andExpect(jsonPath("$.email", is("sergio.crocetta@gmail.com")))
                    .andExpect(jsonPath("$.address", is("Avenida Rivadavia 77")))
                    .andExpect(jsonPath("$.phone", is("15151515111")))
                    .andExpect(jsonPath("$.userType", is("Normal")))
                    .andExpect(jsonPath("$.money", is(Double.valueOf(1000))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(satRecruitmentFileService).createUser(any());
    }

    @Test
    @Order(2)
    void testListar() throws Exception {
        List<UserDTO> usersList = Arrays.asList(Datos.createUser1(),
                Datos.createUser2()
        );
        when(satRecruitmentFileService.findAll()).thenReturn(usersList);

        mvc.perform(get("/api/v1/user/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Sergio"))
                .andExpect(jsonPath("$[0].email").value("sergio.crocetta@gmail.com"))
                .andExpect(jsonPath("$[0].phone").value("15151515111"))
                .andExpect(jsonPath("$[1].name").value("Carlos"))
                .andExpect(jsonPath("$[1].email").value("carlos.lopez@gmail.com"))
                .andExpect(jsonPath("$[1].phone").value("15151515222"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(usersList)));

        verify(satRecruitmentFileService).findAll();
    }

    @Test
    @Order(3)
    void testFindById() throws Exception {
        when(satRecruitmentFileService.findById(1L)).thenReturn(Datos.createUser1());

        mvc.perform(get("/api/v1/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name").value("Sergio"))
                .andExpect(jsonPath("$.email").value("sergio.crocetta@gmail.com"))
                .andExpect(jsonPath("$.address").value("Avenida Rivadavia 77"))
                .andExpect(jsonPath("$.phone").value("15151515111"))
                .andExpect(jsonPath("$.userType").value("Normal"))
                .andExpect(jsonPath("$.money").value(Double.valueOf(1000)));

        verify(satRecruitmentFileService).findById(1L);
    }

}
