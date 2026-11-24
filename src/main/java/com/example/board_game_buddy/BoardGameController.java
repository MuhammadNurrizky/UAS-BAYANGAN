package com.example.board_game_buddy;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardGameController {
	private final BoardGameService boardGameService;

	public BoardGameController(BoardGameService boardGameService) {
		this.boardGameService = boardGameService;
	}

	@PostMapping("/ask")
	public AskResponse ask(@RequestBody AskRequest request) {
		String answer = boardGameService.answer(request.question());
		return new AskResponse(answer);
	}
}
