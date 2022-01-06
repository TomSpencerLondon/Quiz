create table answers
(
    id          bigint not null
        primary key,
    choice_text text not null,
    question_id bigint
        references questions,
    is_correct boolean
);