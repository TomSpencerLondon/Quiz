create table questions
(
    id   BIGINT NOT NULL PRIMARY KEY,
    text TEXT,
    choice_type TEXT
);

create sequence hibernate_sequence;
