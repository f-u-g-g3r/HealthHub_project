import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public userData!: User;
  constructor(private router: Router, public authService: AuthenticationService, private userService: UserService) {
    
  }

  ngOnInit(): void {
    if (!sessionStorage.getItem("token")) {
      console.log(sessionStorage.getItem("token"))
      this.router.navigate(["/login"])
    }
    
    this.userService.getOneUser(sessionStorage.getItem('uid')).subscribe({
      next: (response: User) => {
        this.userData = response
      },
      error: console.error
    });
    
  }


}
