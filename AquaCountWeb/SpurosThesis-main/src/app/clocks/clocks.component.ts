import { Component } from '@angular/core';
import { HttpService } from '../http.service';

export interface Clock {
  clockId: number;
  routeId: string;
  coordinates: string;
}

@Component({
  selector: 'app-clocks',
  templateUrl: './clocks.component.html',
  styleUrls: ['./clocks.component.css']
})
export class ClocksComponent {
  public displayedColumns: string[] = ['clockId', 'routeId', 'coordinates', 'actions'];
  public dataSource: Clock[] = [];

  constructor(private httpService: HttpService) { }

  ngOnInit() {
    this.getAllClocks();
  }

  getAllClocks() {
    this.httpService.getAllClocks().subscribe((response: any) => {
      this.dataSource = response;
    },
      (error) => {
        console.error(error);
      }
    )
  }

  onDeleteClicked(element: any) {
    this.httpService.deleteClock(element.clockid).subscribe((response: any) => {
      this.dataSource = response;
      this.getAllClocks();
    },
      (error) => {
        console.error(error);
      }
    )
  }
}
