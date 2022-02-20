package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizFactoryTest {

    @Test
    void repository_with_one_question_then_quiz_is_created_with_one_question() {
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        inMemoryQuestionRepository.save(question);

        final QuizFactory quizFactory = new QuizFactory(inMemoryQuestionRepository);
        final Quiz quiz = quizFactory.createQuiz();

        assertThat(quiz.questions())
                .hasSize(1);
    }

}
