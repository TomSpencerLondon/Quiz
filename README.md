# Quiz

Quiz App for OCP
https://github.com/jitterted/quizdown

# Java OCP Quiz Application

https://github.com/TomSpencerLondon/Quiz

- Console-Based Version
- Story: display 3 questions on the screen
- Story: display each question, wait for input (ENTER), then display next question, until done
- Story: wait for ANSWER after each question and indicate Right/Wrong
- Create Question class (value object) -- NO NEW BEHAVIOR (“solves primitive obsession”)

# Question knows (add behavior to Question class)

- all possible choices
- the correct answerDbo

# Add behavior to Questioner to

- Display all choices
- Accept an answerDbo selection
- Report is correct or not

# Story: collect choices and grade overall exam

- Quiz knows (add behaviour to Quiz class)
- All questions
- All correct choices
- All incorrect choices
- The Final Grade

<img width="1002" alt="Screenshot 2021-10-20 at 13 33 44" src="https://user-images.githubusercontent.com/27693622/138093618-1148ab61-49c5-43f9-8bb6-a597f76b177b.png">

#### Ubiquitous Language

| Domain                                                                                                          | Definition                            | "Quiz Maker" Bounded Context                                                      | "Quiz Taker"  Bounded Context |
|-----------------------------------------------------------------------------------------------------------------|---------------------------------------|-----------------------------------------------------------------------------------|-------------------------------|
| Question                                                                                                        | Text for question                     | Text for question is completely editable (has no behaviour other than validation) | Text for question Immutable (read-only) (Query) Can ask if Choice is the Answer - delegates to Single/Multiple Choice |                   |                                                                                   |                               |
| Choice | Choice for a question                 | Editable | Immutable |
| Answer | Correct choice(s) for a question      | Editable | not directly visible |
| Single Choice | A question that has only 1 answer     | Editable | Invisible |
| Multiple Choice | A quesiton that has 2 or more answers | Editable | Invisible |
| Quiz | Collection of Questions | Define (editable) | Immutable/ read-only |
| Quiz Taker | Person that is taking the quiz | Does not exist in the Editable Bounded context | Stateful |
| Quiz Session | Quiz being taken by a person, collecting Responses | Does not exist | Stateful |
| Response | Choice selected by Quiz taker | Does not exist | Value Object |
| Grade | How well a Quiz Taker did for a specific, completed Quiz Session | Does not exist | Value Object |
| Explanation | Further information about question | Editable | View only |

#### CRC Diagram

<img width="970" alt="Screenshot 2022-01-22 at 13 13 45" src="https://user-images.githubusercontent.com/27693622/150639932-a91ef97d-8af8-44a7-802d-49bf33ed5777.png">

#### Class Diagram
![Screenshot 2022-01-22 at 22 10 55](https://user-images.githubusercontent.com/27693622/150657074-43c1a264-efb0-4e28-a4ff-a2050258c61c.png)


#### Running Tailwind:

Use:
https://tailwindcss.com/blog/standalone-cli
and run:
./tailwindcss -o ./src/main/resources/static/css/application.css --watch 
