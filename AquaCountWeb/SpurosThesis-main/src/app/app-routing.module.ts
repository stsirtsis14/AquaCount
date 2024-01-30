import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainBoardComponent } from './main-board/main-board.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'mainBoard', component: MainBoardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
