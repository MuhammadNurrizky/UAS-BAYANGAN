package com.example.board_game_buddy;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardGameControllerTests {
	@Test
	void askReturnsServiceAnswer() throws Exception {
		BoardGameService service = question ->
			"Catan cocok untuk permainan 3-4 orang.";
		MockMvc mockMvc = MockMvcBuilders
			.standaloneSetup(new BoardGameController(service))
			.build();

		mockMvc.perform(post("/ask")
				.contentType("application/json")
				.content("{\"question\":\"Game apa untuk 4 pemain?\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.answer").value(
				"Catan cocok untuk permainan 3-4 orang."));
	}
}
