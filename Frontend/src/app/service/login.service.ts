import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import swal from'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Injectable({
    providedIn: 'root'
  })
  export class LoginService {
    uri ;
    token: string = "";
    id: string = "";

    constructor(private http: HttpClient, private router: Router) { 
      this.uri = Propiedades.URL;
    }
    
    login(email: string, password: string) {

      this.http.post<any>(`${this.uri}financiera/auth/login`, { username: email, password: password })
        .subscribe(
          (resp) => {
            this.token = resp.data.token;
            this.id = resp.data.userInfo.id;
            localStorage.setItem('token', this.token);
            localStorage.setItem('id', this.id);
            swal.fire({
              position: "center",
              icon: "success",
              title: "Inicio de sesión correctamente",
              showConfirmButton: false,
              timer: 1500
            });
            Propiedades.flag = true;
            this.router.navigate(['landing']);
          },
          (error) => {
            swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Ocurrió un error en el servidor. Ponte en contacto con soporte",
            });
            console.error('Login failed:', error);
          }
        );
    }
  }