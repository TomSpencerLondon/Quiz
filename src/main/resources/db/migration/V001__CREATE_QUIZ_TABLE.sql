create table multiple_choices
(
    id      bigserial not null
        primary key,
    correct varchar(255)
);

create table multiple_choice_dto_answers
(
    multiple_choice_dto_id bigserial not null
        references multiple_choices,
    answers                varchar(255)
);

create table questions
(
    id                     bigint not null
        primary key,
    text                   varchar(255),
    multiple_choice_dto_id bigint
        references multiple_choices
);
