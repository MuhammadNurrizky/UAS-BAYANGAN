package com.example.board_game_buddy;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationController {
	private final EvaluationService evaluationService;

	public EvaluationController(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@PostMapping("/evaluate")
	public EvaluationResponseDto evaluate(@RequestBody EvaluationRequestDto request) {
		return evaluationService.evaluate(request.question(), request.answer());
	}
}
