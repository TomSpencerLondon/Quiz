create table multiple_choices
(
    id serial not null
        primary key,
    correct varchar(255)
);

create table multiple_choice_dto_answers
(
    multiple_choice_dto_id int4 not null
        references multiple_choices,
    answers                varchar(255)
);

create table questions
(
    id serial not null
        primary key,
    text                   varchar(255),
    multiple_choice_dto_id int4
        references multiple_choices
);
