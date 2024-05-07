import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import swal from 'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterCustomerComponent {
  uri;
  nombreCli = '';
  apellidoCli = '';
  apellidoCli2 = '';
  edad = '';
  correo = '';
  telefono1 = '';
  telefono2 = '';
  numeroCli = '';
  ingresoSem = '';
  domicilio = '';
  detalleDomicilio = '';
  nombreCompletoReferencia = '';
  telefono1Referencia = '';
  telefono2Referencia = '';
  parentezco = '';
  contrato = '';
  folio = '';
  fecha = '';
  diaPago = '';
  monto = '';
  pagoInicial = '';
  pagosemanal = '';
  flag = false;
  flag1 = false;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient) {
    this.uri = Propiedades.URL;
  }

  ValidationDatas() {
    if (this.ValidationCliente()) {
      return
    }
    this.flag = true;
    if (this.ValidationReferencia()) {
      return
    }
    this.flag1 = true;


  }



  ValidationCliente() {
    let flag = false;
    if (this.nombreCli === "" || this.apellidoCli === "" || this.telefono1 === "" || this.domicilio === "" || this.detalleDomicilio === "") {
      flag = true;
    }
    console.log(flag);

    return flag;
  }

  ValidationReferencia() {
    let flag = false;
    if (this.nombreCompletoReferencia === "" || this.telefono1Referencia === "" || this.telefono2Referencia === "" || this.parentezco === "") {
      flag = true;
    }
    console.log(flag);

    return flag;
  }

  logout() {
    localStorage.removeItem('token');
  }
  public get logIn(): boolean {
    return (localStorage.getItem('token') !== null);
  }

  ngOnInit() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }

  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  RegistrarCliente() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.post<any>(`${this.uri}financiera/client/cliente/`, { nombre: this.nombreCli, paterno: this.apellidoCli,materno: this.apellidoCli2,edad: this.edad, ingreso_semanal: this.ingresoSem, telefono_1: this.telefono1,telefono_2: this.telefono2,correo : this.correo, domicilio:this.domicilio,domicilio_detalle: this.detalleDomicilio,cn_nombre: this.nombreCompletoReferencia, cn_telefono_1: this.telefono1Referencia, cn_telefono_2: this.telefono2Referencia,parentezco: this.parentezco},{ headers })
      .subscribe(
        (resp) => {

          swal.fire({
            position: "center",
            icon: "success",
            title: "Se registro Correctamente",
            showConfirmButton: false,
            timer: 1500
          });
          this.router.navigate(['Consultar/Cliente']);
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

