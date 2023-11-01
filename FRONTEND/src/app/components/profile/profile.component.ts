import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/interfaces/requests&responses/authenticationRequest';
import { AuthenticationResponse } from 'src/app/interfaces/requests&responses/authenticationResponse';
import { UpdatedUser } from 'src/app/interfaces/updatedUser';
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
      next: (response: User) => this.userData = response,
      error: console.error
    });

    if (this.userData.familyDoctorId != null) {
      this.getFamilyDoctor(this.userData.familyDoctorId);
    }
  }

  public onUpdateUser(user: UpdatedUser) {
    this.userService.updateUser(sessionStorage.getItem('uid'), user).subscribe({
      next: (response: User) => {
        this.userData = response;
        const authData: AuthenticationRequest = {
          "email": this.userData.email,
          "password": sessionStorage.getItem('password')!.toString()
        };

        this.authService.authenticate(authData).subscribe({
          next: (response: AuthenticationResponse) => {
            sessionStorage.setItem('token', response.token.toString());
          },
          error: console.error
        });
      },
      error: console.error
    });
  }

  private getFamilyDoctor(doctorId: number) {
    
  }


}
