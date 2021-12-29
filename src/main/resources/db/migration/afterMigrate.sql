INSERT INTO multiple_choices (id, correct) VALUES (2, 'Answer 1') ON CONFLICT DO NOTHING;
INSERT INTO questions (multiple_choice_id, text, id) VALUES (2, 'Question 1', 1) ON CONFLICT DO NOTHING;
INSERT INTO answers (question_id, text, id) VALUES (1, 'Answer 1', 3) ON CONFLICT DO NOTHING;
INSERT INTO answers (question_id, text, id) VALUES (1, 'Answer 2', 4) ON CONFLICT DO NOTHING;
INSERT INTO answers (question_id, text, id) VALUES (1, 'Answer 3', 5) ON CONFLICT DO NOTHING;
INSERT INTO answers (question_id, text, id) VALUES (1, 'Answer 4', 6) ON CONFLICT DO NOTHING;

INSERT INTO multiple_choice_answers
(multiple_choice_id, answers)
SELECT 2, 'Answer 1'
WHERE
    NOT EXISTS (
            SELECT multiple_choice_id FROM multiple_choice_answers
            WHERE multiple_choice_id = 2 AND answers = 'Answer 1'
        );
INSERT INTO multiple_choice_answers
(multiple_choice_id, answers)
SELECT 2, 'Answer 2'
WHERE
    NOT EXISTS (
            SELECT multiple_choice_id FROM multiple_choice_answers
            WHERE multiple_choice_id = 2 AND answers = 'Answer 2'
        );
INSERT INTO multiple_choice_answers
(multiple_choice_id, answers)
SELECT 2, 'Answer 3'
WHERE
    NOT EXISTS (
            SELECT multiple_choice_id FROM multiple_choice_answers
            WHERE multiple_choice_id = 2 AND answers = 'Answer 3'
        );
INSERT INTO multiple_choice_answers
(multiple_choice_id, answers)
SELECT 2, 'Answer 4'
WHERE
    NOT EXISTS (
            SELECT multiple_choice_id FROM multiple_choice_answers
            WHERE multiple_choice_id = 2 AND answers = 'Answer 4'
        );