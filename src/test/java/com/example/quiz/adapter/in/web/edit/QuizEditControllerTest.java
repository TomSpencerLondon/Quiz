package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.adapter.out.web.initialChoiceCount.ChoiceCountConfig;
import com.example.quiz.domain.QuestionBuilder;
import com.example.quiz.hexagon.application.CreateQuestionService;
import com.example.quiz.hexagon.application.QuizCreator;
import com.example.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.example.quiz.hexagon.application.port.InMemoryQuizRepository;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.application.port.QuizRepository;
import com.example.quiz.hexagon.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizEditControllerTest {

    private static final QuizCreator DUMMY_QUIZ_CREATOR = null;
    private static final QuestionRepository DUMMY_QUESTION_REPOSITORY = null;
    private static final ChoiceCountConfig DUMMY_CHOICE_COUNT_CONFIG = null;

    @Test
    void viewQuestionsCreatesModelWithQuestions() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, DUMMY_QUIZ_CREATOR, DUMMY_CHOICE_COUNT_CONFIG, new QuestionTransformer());

        final Model model = new ConcurrentModel();
        final String viewName = quizController.viewQuestions(model);
        assertThat(viewName)
                .isEqualTo("view-questions");
        assertThat(model.containsAttribute("questions"))
                .isTrue();
    }

    @Test
    void addQuestionResultsInQuestionAddedAndRedirects() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().build();
        QuestionRepository questionRepository = questionBuilder.questionRepository();

        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, DUMMY_QUIZ_CREATOR, DUMMY_CHOICE_COUNT_CONFIG, new QuestionTransformer());
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(question)
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult("objectName", "error");

        final String redirectPage = quizController.addQuestion(addQuestionForm, bindingResult);

        assertThat(redirectPage)
                .isEqualTo("redirect:/edit/add-question");
        assertThat(questionRepository.findAll())
                .hasSize(1);
    }


    @Test
    void createQuizResultsInQuizAddedAndRedirects() {
        CreateQuestionService createQuestionService = new CreateQuestionService(DUMMY_QUESTION_REPOSITORY);
        QuizRepository quizRepository = new InMemoryQuizRepository();

        QuizCreator quizCreator = new QuizCreator(quizRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, quizCreator, DUMMY_CHOICE_COUNT_CONFIG, new QuestionTransformer());

        Model model = new ConcurrentModel();
        final String redirectPage = quizController.createQuiz(model);

        assertThat(redirectPage)
                .isEqualTo("redirect:/quiz?quizId=0");
        assertThat(quizRepository.findAll())
                .hasSize(1);
    }

    @Test
    void makerShowsAllQuestions() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question singleChoiceQuestion = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();

        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizRepository quizRepository = new InMemoryQuizRepository();

        QuizCreator quizCreator = new QuizCreator(quizRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, quizCreator, DUMMY_CHOICE_COUNT_CONFIG, new QuestionTransformer());

        Model model = new ConcurrentModel();
        quizController.maker(model);

        List<Question> questions = (List<Question>) model.getAttribute("questions");

        assertThat(questions)
                .containsExactly(singleChoiceQuestion);
    }

}
