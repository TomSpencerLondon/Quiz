# Quiz

Quiz App for OCP
https://github.com/jitterted/quizdown

# Java OCP Quiz Application

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

#### Sequence Diagrams

|                                                                  Edit Quiz                                                                  |
|:-------------------------------------------------------------------------------------------------------------------------------------------:|
| ![Screenshot 2022-01-22 at 22 49 08](https://user-images.githubusercontent.com/27693622/150657856-7c007a48-fc4c-4e32-af56-2f01763f7ecd.png) |
| ![Screenshot 2022-01-22 at 22 46 35](https://user-images.githubusercontent.com/27693622/150657804-d522f19e-23f3-4916-a1a3-99e3aba8691c.png) |

|                                                                 Answer Quiz                                                                 |
|:-------------------------------------------------------------------------------------------------------------------------------------------:|
| ![Screenshot 2022-01-23 at 21 30 49](https://user-images.githubusercontent.com/27693622/150698726-f11a8e5a-cf9b-413f-b038-ca88811df73b.png) |
| ![Screenshot 2022-01-23 at 21 45 52](https://user-images.githubusercontent.com/27693622/150699135-19559c40-adfb-4a62-bce8-89959655c392.png) |
| ![Screenshot 2022-01-23 at 21 57 14](https://user-images.githubusercontent.com/27693622/150699437-a89a26a1-4a2c-4c60-a5ce-0de5a23e55b1.png) |

#### Running Tailwind:

Use:
https://tailwindcss.com/blog/standalone-cli
and run:
./tailwindcss -o ./src/main/resources/static/css/application.css --watch 
