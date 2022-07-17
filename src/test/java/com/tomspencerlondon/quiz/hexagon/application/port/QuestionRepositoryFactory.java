package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.domain.factories.SingleChoiceQuestionTestFactory;
import org.jetbrains.annotations.NotNull;

public class QuestionRepositoryFactory {
    @NotNull
    public static QuestionRepository createQuestionRepositoryWithSingleChoiceQuestion() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(singleChoiceQuestion);
        return questionRepository;
    }
}
