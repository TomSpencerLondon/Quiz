import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {QuestionComponent} from "./question/question.component";

const routes: Routes = [
  {path: 'angular', component: QuestionComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
