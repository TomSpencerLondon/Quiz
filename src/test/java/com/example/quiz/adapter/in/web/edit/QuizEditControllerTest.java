package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.adapter.out.web.initialChoiceCount.ChoiceCountConfig;
import com.example.quiz.application.CreateQuestionService;
import com.example.quiz.application.QuizCreator;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.QuizRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceId;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
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
    private static final CreateQuestionService DUMMY_QUESTION_SERVICE = null;
    private static final ChoiceCountConfig DUMMY_CHOICE_COUNT_CONFIG = null;

    @Test
    void viewQuestionsCreatesModelWithQuestions() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, DUMMY_QUIZ_CREATOR, DUMMY_CHOICE_COUNT_CONFIG, new QuestionFactory());

        final Model model = new ConcurrentModel();
        final String viewName = quizController.viewQuestions(model);
        assertThat(viewName)
                .isEqualTo("view-questions");

        assertThat(model.containsAttribute("questions"))
                .isTrue();
    }

    @Test
    void addQuestionResultsInQuestionAddedAndRedirects() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, DUMMY_QUIZ_CREATOR, DUMMY_CHOICE_COUNT_CONFIG, new QuestionFactory());

        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                "single", new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false));

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
        QuizEditController quizController = new QuizEditController(createQuestionService, quizCreator, DUMMY_CHOICE_COUNT_CONFIG, new QuestionFactory());

        Model model = new ConcurrentModel();
        final String redirectPage = quizController.createQuiz(model);

        assertThat(redirectPage)
                .isEqualTo("redirect:/quiz?quizId=0");
        assertThat(quizRepository.findAll())
                .hasSize(1);
    }

    @Test
    void makerShowsAllQuestions() {
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question singleChoiceQuestion = new Question(
                "Question 1",
                new SingleChoice(
                        List.of(
                                new Choice(ChoiceId.of(1L), "Answer 1", true),
                                new Choice(ChoiceId.of(2L), "Answer 2", false),
                                new Choice(ChoiceId.of(3L), "Answer 2", false),
                                new Choice(ChoiceId.of(4L), "Answer 2", false)
                        )));

        questionRepository.save(singleChoiceQuestion);
        CreateQuestionService createQuestionService = new CreateQuestionService(questionRepository);
        QuizRepository quizRepository = new InMemoryQuizRepository();

        QuizCreator quizCreator = new QuizCreator(quizRepository);
        QuizEditController quizController = new QuizEditController(createQuestionService, quizCreator, DUMMY_CHOICE_COUNT_CONFIG, new QuestionFactory());

        Model model = new ConcurrentModel();
        quizController.maker(model);

        List<Question> questions = (List<Question>) model.getAttribute("questions");

        assertThat(questions)
                .containsExactly(singleChoiceQuestion);
    }

}
