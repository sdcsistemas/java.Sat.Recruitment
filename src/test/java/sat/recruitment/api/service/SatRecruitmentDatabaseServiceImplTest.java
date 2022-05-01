package sat.recruitment.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import sat.recruitment.api.Datos;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.model.User;
import sat.recruitment.api.repository.UserRepository;
import sat.recruitment.api.service.impl.SatRecruitmentDatabaseServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SatRecruitmentDatabaseServiceImplTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    SatRecruitmentDatabaseServiceImpl service;

    @Captor
    ArgumentCaptor<Long> captor;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Tag("Find all users")
    void testFindAll() {
        when(repository.findAll()).thenReturn(Datos.USERS);
        List<UserDTO> userDTO = service.findAll();
        assertTrue(userDTO.size() > 0);
        assertEquals(5L, userDTO.get(0).getId());
        assertEquals("Sergio", userDTO.get(0).getName());
        assertEquals("sergio.crocetta@gmail.com", userDTO.get(0).getEmail());
        assertEquals("1515151555", userDTO.get(0).getPhone());

        assertEquals(6L, userDTO.get(1).getId());
        assertEquals("Matias", userDTO.get(1).getName());
        assertEquals("matias.gonzalez@gmail.com", userDTO.get(1).getEmail());
        assertEquals("15151515666", userDTO.get(1).getPhone());
    }

    @Test
    @Tag("Find by id user")
    void testFindById() {
        when(repository.findById(7L)).thenReturn(Datos.getUser1());
        UserDTO user = service.findById(7L);
        assertTrue(user != null);
        assertEquals(7L, user.getId());
        assertEquals("Romina", user.getName());
    }

    @Test
    @Tag("insert user")
    void testInsertUser() {
        UserDTO newUser = Datos.createUser1();

        when(repository.save(any(User.class))).then(new Answer<User>() {

            Long secuencia = 8L;

            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                User user = invocation.getArgument(0);
                user.setId(secuencia++);
                return user;
            }
        });

        UserDTO userDTO = service.insert(newUser);

        assertNotNull(userDTO.getId());
        assertEquals(8L, userDTO.getId());
        assertEquals("Sergio", userDTO.getName());
        assertEquals("sergio.crocetta@gmail.com", userDTO.getEmail());
        assertEquals("15151515111", userDTO.getPhone());

        verify(repository).save(any(User.class));
    }

}