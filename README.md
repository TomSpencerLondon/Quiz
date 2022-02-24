# Quiz

Quiz App for OCP
https://github.com/jitterted/quizdown

# Java OCP Quiz Application

#### Ubiquitous Language

| Domain          | Definition                                                       | "Quiz Maker" Bounded Context                                                     | "Quiz Taker"  Bounded Context                                                                                         |
|-----------------|------------------------------------------------------------------|----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| Question        | Text for question                                                | Text for question is completely editable (has no behaviour other than validation) | Text for question Immutable (read-only) (Query) Can ask if Choice is the Answer - delegates to Single/Multiple Choice |                   |                                                                                   |                               |
| Choice          | Choice for a question                                            | Editable                                                                         | Immutable                                                                                                             |
| Answer          | Correct choice(s) for a question                                 | Editable                                                                         | not directly visible                                                                                                  |
| Single Choice   | A question that has only 1 answer                                | Editable                                                                         | Invisible                                                                                                             |
| Multiple Choice | A question that has 2 or more answers                            | Editable                                                                         | Invisible                                                                                                             |
| Quiz            | Collection of Questions                                          | Define (editable)                                                                | Immutable/ read-only                                                                                                  |
| Quiz Taker      | Person that is taking the quiz                                   | Does not exist in the Editable Bounded context                                   | Stateful                                                                                                              |
| Quiz Session    | Quiz being taken by a person, collecting Responses               | Does not exist                                                                   | Stateful                                                                                                              |
| Response        | Choice selected by Quiz taker                                    | Does not exist                                                                   | Value Object                                                                                                          |
| Grade           | How well a Quiz Taker did for a specific, completed Quiz Session | Does not exist                                                                   | Value Object                                                                                                          |
| Explanation     | Further information about question                               | Editable                                                                         | View only                                                                                                             |
| Category        | Type of question - single, multiple, fill-in                     | Editable                                                                         | View only                                                                                                             |

#### CRC Diagram

|                                                                             CRC Diagram                                                                             |
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|<img width="970" alt="Screenshot 2022-01-22 at 13 13 45" src="https://user-images.githubusercontent.com/27693622/150639932-a91ef97d-8af8-44a7-802d-49bf33ed5777.png">|

#### Class Diagram

|                                                                Class Diagram                                                                |
|:-------------------------------------------------------------------------------------------------------------------------------------------:|
| ![Screenshot 2022-01-22 at 22 10 55](https://user-images.githubusercontent.com/27693622/150657074-43c1a264-efb0-4e28-a4ff-a2050258c61c.png) |

#### Sequence Diagrams

|                                                                     Edit Quiz                                                                    |
|:------------------------------------------------------------------------------------------------------------------------------------------------:|
|    ![Screenshot 2022-01-23 at 22 07 45](https://user-images.githubusercontent.com/27693622/150699808-65f490cb-8018-42ac-a049-8d99cb5f9ea9.png)   |
|![addQuestion-sequenceDiagram-Add_Question](https://user-images.githubusercontent.com/27693622/154843182-412bae9a-485f-46b5-b19a-db4a7aff4140.png)|

|                                                                 Answer Quiz                                                                 |
|:-------------------------------------------------------------------------------------------------------------------------------------------:|
| ![Screenshot 2022-01-23 at 21 30 49](https://user-images.githubusercontent.com/27693622/150698726-f11a8e5a-cf9b-413f-b038-ca88811df73b.png) |
| ![Screenshot 2022-01-23 at 21 45 52](https://user-images.githubusercontent.com/27693622/150699135-19559c40-adfb-4a62-bce8-89959655c392.png) |
| ![Screenshot 2022-01-23 at 22 03 58](https://user-images.githubusercontent.com/27693622/150699647-cba6c338-97b2-43a3-b5d7-1c143c5dc68b.png) |

|                                                  Hexagonal Diagrams                                                   |
|:---------------------------------------------------------------------------------------------------------------------:|
| ![hex-diagram](https://user-images.githubusercontent.com/27693622/154844920-747373d7-aebe-48a0-88c3-b75f87011217.png) |

|                                               Design for Multiple and Single Choice Questions                                                |
|:--------------------------------------------------------------------------------------------------------------------------------------------:|
|                                          1. [Current] Value for the radio button is the choice text                                          |
|    ![current-AskSingleChoiceForm](https://user-images.githubusercontent.com/27693622/155522088-a98e19fd-049c-4bee-88cb-0355c1a01560.png)     |
|                               2. [Change in Question structure] respondWith(int) — int is the "Correct Answer"                               |
| ![index_to_list-design-AskChoiceForm](https://user-images.githubusercontent.com/27693622/155524082-4c35043f-5330-44f4-aa33-0fe56b1f3355.png) |

#### Running Tailwind:

Use:
https://tailwindcss.com/blog/standalone-cli
and run:
./tailwindcss -o ./src/main/resources/static/css/application.css --watch 
