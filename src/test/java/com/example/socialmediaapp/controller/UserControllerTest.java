package com.example.socialmediaapp.controller;

import com.example.socialmediaapp.dto.UserProfileDto;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@DisplayName("User controller tests")
@Slf4j
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    private String userJson;

    static List<UserProfileDto> userList;
    private User user;
    private int userId;

    @BeforeAll
    static void beforeAll() {
        userList = new ArrayList<>();
        userList.add(new UserProfileDto("likita", "rai", "developer", LocalDate.of(2001, 11, 1), "F"));
        userList.add(new UserProfileDto("dhruvi", "patel", "developer", LocalDate.of(2001, 11, 1), "F"));
    }

    @AfterAll
    static void afterAll() {
        userList.clear();
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        userId = 3;
        user = new User(userId, "rutu", "patel", null);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        userJson = objectWriter.writeValueAsString(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userId = -1;
        userJson = "";
    }

    @Nested
    @DisplayName("Get all users information from database")
    class GetAllUsersTests{
        @Test
        @DisplayName("Get information when no user exists")
        void testGetAllUsers_WhenNoUsersExist() throws Exception {
            when(userService.getAllUsers()).thenReturn(new ArrayList<>());

            mockMvc.perform(get("/user/").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string("There are no users"));

            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("Get all users information from database when user exists")
        void testGetAllUsers_WhenUsersExist() throws Exception {
            when(userService.getAllUsers()).thenReturn(userList);

            mockMvc.perform(get("/user/").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(header().string("Content-Type", "application/json"))
                    .andDo(print());

            verify(userService).getAllUsers();
        }
    }

    @Nested
    @DisplayName("Add user into database")
    class AddUserTests{
        @Test
        @DisplayName("User added successfully")
        void testAddUser_WhenUserIsValid() throws Exception {
            when(userService.addUser(user)).thenReturn(user);

            mockMvc.perform(post("/user/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("User added successfully"));

            verify(userService, times(1)).addUser(user);
        }

        @Test
        @DisplayName("Object is empty")
        void testAddUser_WhenUserIsNull() throws Exception {
            when(userService.addUser(null)).thenReturn(null);

            mockMvc.perform(post("/user/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Object is empty"));
        }
    }

    @Nested
    @DisplayName("Edit user in database")
    class EditUserTests{
        @Test
        @DisplayName("User updated successfully")
        void testEditUser_WhenUserExists() throws Exception {
            when(userService.updateUser(eq(user))).thenReturn(1);

            mockMvc.perform(put("/user/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("User updated successfully"));
        }

        @Test
        @DisplayName("User doesn't exist")
        void testEditUser_WhenUserDoesNotExist() throws Exception {
            when(userService.updateUser(user)).thenReturn(0);

            mockMvc.perform(put("/user/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("User with id = " + userId + " doesn't exist"));
        }
    }

    @Nested
    @DisplayName("Delete user from database")
    class DeleteUserTests{
        @Test
        @DisplayName("User deleted successfully")
        void testDeleteUser_WhenUserExists() throws Exception {
            when(userService.deleteUser(userId)).thenReturn("Success");
            mockMvc.perform(delete("/user/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("User deleted successfully"));
        }

        @Test
        @DisplayName("User doesn't exist")
        void testDeleteUser_WhenUserDoesNotExist() throws Exception {
            when(userService.deleteUser(3)).thenReturn("Fail");

            mockMvc.perform(delete("/user/3")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("User with id = 3 doesn't exist"));
        }

    }
}