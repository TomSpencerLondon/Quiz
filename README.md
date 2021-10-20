# Quiz
Quiz App for OCP
https://github.com/jitterted/quizdown


Java OCP Quiz Application
Q: where to get test information from
Initially just a text file
Later: some other storage?
Q: how to display question?
Q: submit & show correct answer
Q: overall grade
Later: show all wrongly answered questions (so you can focus on them)

https://github.com/TomSpencerLondon/Quiz
Console-Based Version
Story: display 3 questions on the screen
Story: display each question, wait for input (ENTER), then display next question, until done
Story: wait for ANSWER after each question and indicate Right/Wrong
Create Question class (value object) -- NO NEW BEHAVIOR (“solves primitive obsession”)
Question knows (add behavior to Question class)
all possible answers
the correct answer
Add behavior to Questioner to
Display all answers
Accept an answer selection
Report is correct or not
Story: collect answers and grade overall exam
Quiz knows (add behaviour to Quiz class) 
All questions
All correct answers
All incorrect answers
The Final Grade


<img width="1002" alt="Screenshot 2021-10-20 at 13 33 44" src="https://user-images.githubusercontent.com/27693622/138093618-1148ab61-49c5-43f9-8bb6-a597f76b177b.png">

