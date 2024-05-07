import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import swal from'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Injectable({
    providedIn: 'root'
  })
  export class ContractoService {
    uri ;
    data: string = "";
    
    constructor(private http: HttpClient, private router: Router) { 
      this.uri = Propiedades.URL;
    }
    getContracto() {

      this.http.get<any>(`${this.uri}financiera/contrato/pdf`)
        .subscribe(
          (resp) => {
            this.data = resp.data;
            console.log(this.data);
            
            swal.fire({
              position: "center",
              icon: "success",
              title: "Se trajo el contracto Correctamente",
              showConfirmButton: false,
              timer: 1500
            });
            return this.data;
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