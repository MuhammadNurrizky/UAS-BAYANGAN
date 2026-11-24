package com.example.board_game_buddy;

public record EvaluationResponseDto(
	boolean passed,
	float score,
	String feedback
) {
}
