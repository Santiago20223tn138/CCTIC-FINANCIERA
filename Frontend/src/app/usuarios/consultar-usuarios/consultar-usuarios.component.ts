import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import swal from 'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-consultar-usuarios',
  templateUrl: './consultar-usuarios.component.html',
  styleUrls: ['./consultar-usuarios.component.css']
})
export class ConsultarUsuariosComponent implements OnInit {
  uri;
  usuarios = [];
  page!: number;
  searchText: any;
  seleccionado: string = "";
  nombre = '';
  apellidos = '';
  telefono = '';
  email = '';
  password = '';
  passwordconfirm = '';
  domicilio = '';
  flag = false;
  passwordUpdate = '';
  passwordConfirmUpdate = '';
  CorreoUpdate = '';
  IdUpdate = 0;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient) {
    this.uri = Propiedades.URL;
  }

  mandarDatosUpdate(id: number,correo :string){
    this.CorreoUpdate = correo;
    this.IdUpdate = id;
    console.log(this.CorreoUpdate +"  "+this.IdUpdate );
    
  }

  logout() {
    localStorage.removeItem('token');
  }

  ngOnInit() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.http.get<any>(this.uri + 'financiera/user/', { headers }).subscribe(data => {
      this.usuarios = data.data;
      console.log(data.data);

    });
  }

  registrarUsuario() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.ValidationData();

    if (!this.flag) {
      return
    }
    const body = {
      name: this.nombre,
      lastName: this.apellidos,
      address: this.domicilio,
      phone: this.telefono,
      email: this.email,
    };


    this.http.post<any>(`${this.uri}financiera/person/`, body, { headers })
      .subscribe(
        (resp) => {
          const personaId = resp.data.id; // Este debe ser el ID real obtenido de la respuesta

          const bodyCuenta = {
            password: this.password,
            person: {
              id: personaId, 
            },
            status: 1,
            authorities: [{
              id: this.seleccionado,
            }],
          };
          this.http.post<any>(`${this.uri}financiera/user/`, bodyCuenta, { headers })
            .subscribe(
              (resp) => {
                swal.fire({
                  position: "center",
                  icon: "success",
                  title: "Se registro Correctamente",
                  showConfirmButton: false,
                  timer: 1500
                });
                window.location.reload();
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

  ValidationUsuario() {
    let flag = false;
    if (!this.nombre || !this.apellidos || !this.telefono || !this.email || !this.password || !this.passwordconfirm || !this.domicilio) {
      flag = true;
    }
    return flag;
  }

  ValidationCorreo() {
    let flag = false;
    if ((this.password !== this.passwordconfirm) && (this.password.length < 8 || this.passwordconfirm.length < 8)) {
      flag = true;
    }
    return flag;
  }

  ValidationData() {
    console.log(this.flag);
    if (this.ValidationUsuario()) {
      swal.fire({
        icon: "warning",
        title: "Atención",
        text: "Todos los campos son obligatorios.",
      });
      return
    }
    if (this.ValidationCorreo()) {
      swal.fire({
        icon: "error",
        title: "Error",
        text: "Las contraseñas no coinciden o tienen menos de 8 caracteres.",
      });
      return
    }

    this.flag = true;
  }

  UpdateUsuario(){
    if (this.passwordUpdate === "" || this.passwordConfirmUpdate === "" ) {
      swal.fire({
        icon: "error",
        title: "Error",
        text: "Las contraseñas no pueden estar vacias.",
      });
      return; // Detenemos la ejecución
    }
    if (this.passwordUpdate !== this.passwordConfirmUpdate || this.passwordUpdate.length < 8) {
      swal.fire({
        icon: "error",
        title: "Error",
        text: "Las contraseñas no coinciden o tienen menos de 8 caracteres.",
      });
      return; // Detenemos la ejecución
    }

    const body = {
      id: this.IdUpdate,
      password: this.passwordUpdate,
    };
    console.log(body);
    
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.put<any>(`${this.uri}financiera/user/password/`, body, { headers })
    .subscribe(
      (resp) => {
        swal.fire({
          position: "center",
          icon: "success",
          title: "Se Actualizo Correctamente",
          showConfirmButton: false,
          timer: 2500
        });
        window.location.reload();
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
  
  updateStatusUsuario(id: number){
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const body = {
    };
    this.http.put<any>(`${this.uri}financiera/user/status/`+id, body,{ headers })
    .subscribe(
      (resp) => {
        swal.fire({
          position: "center",
          icon: "success",
          title: "Se Actualizo Correctamente",
          showConfirmButton: false,
          timer: 1500
        });
        window.location.reload();
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
