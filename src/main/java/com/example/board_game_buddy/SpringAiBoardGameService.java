package com.example.board_game_buddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {
	private final ChatClient chatClient;

	public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
			.defaultSystem("""
				Anda adalah asisten Board Game Buddy.
				Bantu pengguna memilih dan memahami board game.
				Jawab singkat, jelas, dan berdasarkan informasi yang diberikan.
				Jika tidak yakin, katakan bahwa informasi itu perlu diperiksa.
				""")
			.build();
	}

	@Override
	public String answer(String question) {
		return chatClient.prompt()
			.user(question)
			.call()
			.content();
	}
}
