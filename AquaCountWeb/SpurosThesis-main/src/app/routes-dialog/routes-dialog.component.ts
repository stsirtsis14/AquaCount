import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpService } from '../http.service';

export interface Measurement {
  clockid: number,
  value: string,
  timestamp: string
}

@Component({
  selector: 'app-routes-dialog',
  templateUrl: 'routes-dialog.component.html',
  styleUrls: ['routes-dialog.component.css']
})
export class RoutesDialogComponent {
  public routeClocks: number[] = [];
  public measurements: Measurement[] = [];
    
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private httpService: HttpService) {}

  ngOnInit() {
    this.routeClocks = this.data.response;
  }

  getClockMeasurements(id: number) {
    this.httpService.getClockMeasurements(id).subscribe((response: any) => {
      this.measurements = response;
    },
      (error) => {
        console.error(error);
      }
    )
  }
  
}
