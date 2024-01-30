import { Component } from '@angular/core';
import { HttpService } from '../http.service';
import { MeasurementsDialogComponent } from '../measurements-dialog/measurements-dialog.component';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogTitle,
  MatDialogContent,
} from '@angular/material/dialog';


export interface Counter {
  counterId: number;
  firstName: string;
  lastName: string;
  username: string;
  authority: string;
}

@Component({
  selector: 'app-counters',
  templateUrl: './counters.component.html',
  styleUrls: ['./counters.component.css']
})
export class CountersComponent {
  public displayedColumns: string[] = ['counterId', 'firstName', 'lastName', 'username', 'authority', 'actions'];
  public dataSource: Counter[] = [];

  constructor(private httpService: HttpService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getAllCounters();
  }

  getAllCounters() {
    this.httpService.getAllCounters().subscribe((response: any) => {
      this.dataSource = response;
    },
      (error) => {
        console.error(error);
      }
    )
  }

  onDeleteClicked(element: any) {
    this.httpService.deleteCounter(element.counterid).subscribe((response: any) => {
      this.dataSource = response;
      this.getAllCounters();
    },
      (error) => {
        console.error(error);
      }
    )
  }

  openDialog(element: any) {
    this.httpService.getCounterMeasurements(element.counterid).subscribe((response: any) => {
      let dialogRef = this.dialog.open(MeasurementsDialogComponent, {
        data: { response }
      });
    },
      (error) => {
        console.error(error);
      }
    )
  }
}
