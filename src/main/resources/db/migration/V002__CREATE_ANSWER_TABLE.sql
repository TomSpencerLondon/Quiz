create table answers
(
    id bigint not null
        primary key,
    text                   text,
    question_id bigint
        references questions
);