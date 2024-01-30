import { Component } from '@angular/core';
import { HttpService } from '../http.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-board',
  templateUrl: './main-board.component.html',
  styleUrls: ['./main-board.component.css']
})
export class MainBoardComponent {
  public tableIndex = 0;
  public userToken: string | null = '';

  constructor(private httpService: HttpService, private router: Router) {}

  ngOnInit() {
    this.userToken = this.httpService.getToken();
  }

  onTableClicked(index: number): void {
    this.tableIndex = index;
  }

  onLogoutClicked(): void {
    this.httpService.removeToken();
    this.router.navigate(['/']);
  }
  
}
