import { Component } from '@angular/core';
import { HttpService } from '../http.service';
import { UserCredentials } from 'src/assets/interfaces/user-credentials';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  public username: string = "";
  public password: string = "";

  constructor(private httpService: HttpService, private router: Router) {}

  ngOnInit() {
  }

  login() {
    const sample: UserCredentials = {
      userCredentials: {
        username: this.username,
        password: this.password
      }
    };
    this.httpService.login(sample).subscribe(
      (response) => {
        const token = response.headers.get('Authorization');
        this.httpService.saveToken(token);
        if (response) {
          this.router.navigate(['/mainBoard']);
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
