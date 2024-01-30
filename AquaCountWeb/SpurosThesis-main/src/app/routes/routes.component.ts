import { Component } from '@angular/core';
import { HttpService } from '../http.service';
import { RoutesDialogComponent } from '../routes-dialog/routes-dialog.component';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogTitle,
  MatDialogContent,
} from '@angular/material/dialog';


export interface Route {
  routeId: number;
  counterId: string;
}

@Component({
  selector: 'app-routes',
  templateUrl: './routes.component.html',
  styleUrls: ['./routes.component.css']
})
export class RoutesComponent {
  public displayedColumns: string[] = ['routeId', 'counterId', 'actions'];
  public dataSource: Route[] = [];

  constructor(private httpService: HttpService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getAllRoutes();
  }

  openDialog(element: any) {
    this.httpService.getRouteClocks(element.routeid).subscribe((response: any) => {
      let dialogRef = this.dialog.open(RoutesDialogComponent, {
        data: { response }
      });
    },
      (error) => {
        console.error(error);
      }
    )
  }

  getAllRoutes() {
    this.httpService.getAllRoutes().subscribe((response: any) => {
      this.dataSource = response;
    },
      (error) => {
        console.error(error);
      }
    )
  }

  onAddClicked() {
    // const newRow = {"routeId": 0, "counterId": ""}
    // this.dataSource = [...this.dataSource, newRow];
    console.log('onAddClicked() called');
    const newRow: Route = { routeId: 0, counterId: "" };
    this.dataSource = [...this.dataSource, newRow];
    //this.getAllRoutes();
  }

  onSaveClicked() {}

  onDeleteClicked(element:any) {
    this.httpService.deleteRoute(element.routeid).subscribe((response: any) => {
      this.dataSource = response;
      this.getAllRoutes();
    },
      (error) => {
        console.error(error);
      }
    )
  }

}
