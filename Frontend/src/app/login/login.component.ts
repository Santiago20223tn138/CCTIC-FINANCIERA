import { Component, ViewChild } from '@angular/core';
import { LoginService } from '../service/login.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import swal from'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  contador = 0;
  constructor(private authService: LoginService, private router: Router) {
  }

  Login() {
    if(this.Validation()){
      return
    }
    this.authService.login(this.email, this.password)

  }

  ngOnInit() {

  }


  Validation() {
    let flag = false;
    if (this.email === "" || this.password === "") {
      swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Los valores ingresados no pueden estar vacios!",
      });
      flag=true;
    }

    if (!this.isValidEmail(this.email)) {
      swal.fire({
        icon: "error",
        title: "Oops...",
        text: "El correo es invalido!",
      });    
      flag=true;
    }

    if (this.email.length < 3 || this.password.length < 3) {
      swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Los datos ingresados son muy cortos!",
      });    
      flag=true;
    }
    return flag;
  }

  ngAfterViewInit() {
    console.log('ngAfterViewInit ejecutado');
    if(Propiedades.flag == true){
      console.log('a a');
      Propiedades.flag = true;
      window.location.reload();
    };
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
}