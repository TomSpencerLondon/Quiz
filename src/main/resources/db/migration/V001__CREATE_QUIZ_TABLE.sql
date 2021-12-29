create table multiple_choices
(
    id bigint not null
        primary key,
    correct text
);

create table multiple_choice_answers
(
    multiple_choice_id bigint not null
        references multiple_choices,
    answers                text
);

create table questions
(
    id bigint not null
        primary key,
    text                   text,
    multiple_choice_id bigint
        references multiple_choices
);

create sequence hibernate_sequence;
