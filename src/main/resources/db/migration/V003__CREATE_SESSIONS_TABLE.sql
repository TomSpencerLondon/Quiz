CREATE TABLE quiz_sessions
(
    id                  BIGINT    NOT NULL PRIMARY KEY,
    started_at          TIMESTAMP NOT NULL,
    token               TEXT      NOT NULL,
    current_question_id BIGINT
);

CREATE TABLE responses
(
    id         BIGINT NOT NULL PRIMARY KEY,
    session_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL
);

CREATE TABLE response_choices
(
    response_id BIGINT NOT NULL,
    choice_id   BIGINT NOT NULL
);
