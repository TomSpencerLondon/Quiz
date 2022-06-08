import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  questions: any;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get<any>('/api/questions').subscribe(data => {
      this.questions = data;
      console.log(data);
    });
  }
}
