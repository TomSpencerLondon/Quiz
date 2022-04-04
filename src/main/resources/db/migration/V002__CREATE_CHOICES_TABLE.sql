create table choices
(
    id          BIGINT  NOT NULL PRIMARY KEY,
    question_id BIGINT,
    choice_text TEXT    NOT NULL,
    is_correct  BOOLEAN NOT NULL
)