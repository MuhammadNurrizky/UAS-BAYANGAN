package com.example.board_game_buddy;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoardGameBuddyApplicationTests {
	private static final WireMockServer wireMock = new WireMockServer(0);

	static {
		wireMock.start();
	}

	@Autowired
	private BoardGameService boardGameService;

	@DynamicPropertySource
	static void configureProvider(DynamicPropertyRegistry registry) {
		registry.add("spring.ai.openai.base-url", wireMock::baseUrl);
		registry.add("spring.ai.openai.api-key", () -> "test-api-key");
		registry.add("spring.ai.openai.chat.options.model", () -> "gpt-6-luna");
	}

	@AfterAll
	static void stopWireMock() {
		wireMock.stop();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void boardGameServiceUsesOpenAiCompatibleApi() {
		wireMock.stubFor(post(anyUrl())
			.willReturn(okJson("""
				{
				  "id": "chatcmpl-test",
				  "object": "chat.completion",
				  "created": 1732400000,
				  "model": "gpt-6-luna",
				  "choices": [{
				    "index": 0,
				    "message": {
				      "role": "assistant",
				      "content": "Catan cocok untuk empat pemain."
				    },
				    "finish_reason": "stop"
				  }]
				}
				""")));

		String answer = boardGameService.answer("Game apa untuk 4 pemain?");

		assertEquals("Catan cocok untuk empat pemain.", answer);
		wireMock.verify(postRequestedFor(anyUrl())
			.withRequestBody(containing("Game apa untuk 4 pemain?")));
	}
}
