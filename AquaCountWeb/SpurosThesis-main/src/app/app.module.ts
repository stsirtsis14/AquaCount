import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { MainBoardComponent } from './main-board/main-board.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RoutesDialogComponent } from './routes-dialog/routes-dialog.component';
import { MeasurementsDialogComponent } from './measurements-dialog/measurements-dialog.component';
import { ClocksComponent } from './clocks/clocks.component';
import { CountersComponent } from './counters/counters.component';
import { RoutesComponent } from './routes/routes.component';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog'
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainBoardComponent,
    RoutesComponent,
    CountersComponent,
    ClocksComponent,
    MeasurementsDialogComponent,
    RoutesDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatListModule,
    MatSelectModule,
    MatTableModule,
    HttpClientModule,
    FormsModule,
    MatIconModule,
    MatDialogModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
