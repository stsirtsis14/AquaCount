import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface Measurement {
  clockid: number,
  value: string,
  timestamp: string
}

@Component({
  selector: 'app-measurements-dialog',
  templateUrl: './measurements-dialog.component.html',
  styleUrls: ['./measurements-dialog.component.css']
})
export class MeasurementsDialogComponent {
  public measurements: Measurement[] = [];
    
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.measurements = this.data.response;
  }
}
