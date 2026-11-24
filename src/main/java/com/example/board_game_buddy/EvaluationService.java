package com.example.board_game_buddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
	private final RelevancyEvaluator relevancyEvaluator;
	private final FactCheckingEvaluator factCheckingEvaluator;

	public EvaluationService(ChatClient.Builder chatClientBuilder) {
		this.relevancyEvaluator = RelevancyEvaluator.builder()
			.chatClientBuilder(chatClientBuilder)
			.build();
		this.factCheckingEvaluator = FactCheckingEvaluator
			.forBespokeMinicheck(chatClientBuilder);
	}

	public EvaluationResponseDto evaluate(String question, String answer) {
		EvaluationRequest request = new EvaluationRequest(question, answer);

		EvaluationResponse relevancy = relevancyEvaluator.evaluate(request);
		EvaluationResponse factual = factCheckingEvaluator.evaluate(request);

		boolean passed = relevancy.isPass() && factual.isPass();
		float score = (relevancy.getScore() + factual.getScore()) / 2.0f;
		String feedback = "Relevansi: " + relevancy.getFeedback()
			+ " Fakta: " + factual.getFeedback();
		return new EvaluationResponseDto(passed, score, feedback);
	}
}
