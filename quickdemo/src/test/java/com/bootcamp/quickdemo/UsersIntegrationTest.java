
package com.bootcamp.quickdemo;

import com.bootcamp.quickdemo.model.UserModel;
import com.bootcamp.quickdemo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private UserModel user1;
	private UserModel user2;

	@BeforeEach
	public void setup() {
		userRepository.deleteAll();

		user1 = UserModel.builder().name("John Doe").email("john@example.com").password("pass1").build();
		user2 = UserModel.builder().name("Jane Smith").email("jane@example.com").password("pass2").build();

		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
	}

	@Test
	public void testGetAllUsers() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is("John Doe")))
				.andExpect(jsonPath("$[1].email", is("jane@example.com")));
	}

	@Test
	public void testGetUserById() throws Exception {
		mockMvc.perform(get("/users/{id}", user1.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("John Doe")))
				.andExpect(jsonPath("$.email", is("john@example.com")));
	}

	@Test
	public void testCreateUser() throws Exception {
		UserModel newUser = UserModel.builder().name("Alice Wonderland").email("alice@example.com").password("pass3").build();

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("Alice Wonderland")))
				.andExpect(jsonPath("$.email", is("alice@example.com")));
	}

	@Test
	public void testUpdateUser() throws Exception {
		user1.setName("John Updated");
		user1.setEmail("john.updated@example.com");

		mockMvc.perform(put("/users/{id}", user1.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("John Updated")))
				.andExpect(jsonPath("$.email", is("john.updated@example.com")));
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/users/{id}", user1.getId()))
				.andExpect(status().isOk());

		Optional<UserModel> deletedUser = userRepository.findById(user1.getId());
		assert(deletedUser.isEmpty());
	}
}

