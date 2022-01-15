create table questions_choices (
    question_dbo_id bigint,
    choice_text text not null,
    is_correct boolean
)