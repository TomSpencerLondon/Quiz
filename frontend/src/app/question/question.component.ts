import { Component, OnInit, Input } from '@angular/core';

interface Question {
  text: string;
  choices: any;
  answer: string;
}

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})

export class QuestionComponent implements OnInit {

  @Input() question!: Question;
  constructor() { }

  ngOnInit(): void {
  }

}
