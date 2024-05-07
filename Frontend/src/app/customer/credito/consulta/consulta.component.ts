import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { ActivatedRoute } from '@angular/router';
import { Credito } from './Credito.Model';
import swal from 'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-consulta',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css']
})
export class ConsultaComponent implements OnInit {
  lista:string[]=["LUNES","MARTES","MIERCOLES", "JUEVES","VIERNES","SABADO","DOMINGO"
];
  seleccionado: string = "";
  id: number;
  diaPago: string = "";
  lunes: string = "LUNES";
  page!: number;

  folio: string = "";
  monto: number = 0;
  montoDiaDepago: number = 0;
  inicial: number = 0;
  semanal: number = 0;
  hoy = new Date();
  base64: any;
  pagoinicialseleccionado : number = 0;
  Idseleccionado : number = 0;

  year = this.hoy.getFullYear();
  month = ('0' + (this.hoy.getMonth() + 1)).slice(-2); // Agrega un cero adelante si es necesario
  day = ('0' + this.hoy.getDate()).slice(-2);

  uri;
  token: string = "";
  searchText: any;
  creditos: Credito[] = [];

  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private activateRoute: ActivatedRoute) {
    this.id = + activateRoute.snapshot.paramMap.get('id')!;
    this.uri = Propiedades.URL;

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

    this.http.get<any>(`${this.uri}financiera/client/` + this.id, { headers }).subscribe(data => {
      this.creditos = data.data;
    });
  }

  onSelectChange() {
    // Aquí puedes realizar acciones cuando cambia la selección
    console.log('Valor seleccionado:', this.diaPago);
  }

  registrarCredito() {
    const token = localStorage.getItem('token');
    const id = localStorage.getItem('id');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const fechaFormateada = `${this.year}-${this.month}-${this.day}`;

    this.http.post<any>(`${this.uri}financiera/client/credito/`, { diaPago: this.diaPago, monto: this.monto, inicial: this.inicial, semanal: this.semanal, cliente: this.id, fecha: fechaFormateada,promotora: id }, { headers })
      .subscribe(
        (resp) => {
          swal.fire({
            position: "center",
            icon: "success",
            title: "Se registro correctamente",
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

  registrarDiaDePago() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const fechaFormateada = `${this.year}-${this.month}-${this.day}`;

    this.http.put<any>(`${this.uri}/client/credito/`, { diaPago: this.seleccionado, id : this.Idseleccionado }, { headers })
      .subscribe(
        (resp) => {
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

      this.http.post<any>(`${this.uri}/credito/`, { fecha: fechaFormateada, monto: this.montoDiaDepago, folio: this.folio, credito: this.Idseleccionado }, { headers })
      .subscribe(
        (resp) => {
          swal.fire({
            position: "center",
            icon: "success",
            title: "Se registro correctamente",
            showConfirmButton: false,
            timer: 1500
          });
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
      this.http.get<any>(`${this.uri}/recibo-pago/pdf`, { headers })
      .subscribe(
        (data) => {
          this.base64 = data.data;
          this.downloadPdf(this.base64,"Recibo de Pago","");
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

  descagar(id: number,contracto :string) {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    swal.fire({
      title: 'Descargando los documentos',
      showLoaderOnConfirm: true,
    });

    this.http.get<any>(this.uri+'financiera/contrato/pdf/' + id, { headers }).subscribe(data => {
      this.base64 = data.data;
      this.downloadPdf(this.base64,"Contrato",contracto); // Move this line inside the subscribe block
    });
    this.http.get<any>(this.uri+'financiera/registro-pago/pdf/' + id, { headers }).subscribe(data => {
      this.base64 = data.data;
      this.downloadPdf(this.base64,"Registro-Pago",contracto); // Move this line inside the subscribe block
    });
    this.http.get<any>(this.uri+'financiera/responsiva/pdf/' + id, { headers }).subscribe(data => {
      this.base64 = data.data;
      this.downloadPdf(this.base64,"Responsiva",contracto); // Move this line inside the subscribe block
    });
    this.http.get<any>(this.uri+'financiera/pagare/pdf/' + id, { headers }).subscribe(data => {
      this.base64 = data.data;
      this.downloadPdf(this.base64,"Pagare",contracto); // Move this line inside the subscribe block
    });

    swal.close;
  }


  seleccionarElPago(monto: number,id: number) {   
    this.pagoinicialseleccionado = monto;
    this.Idseleccionado = id;
  }

  downloadPdf(base64String: string,nombre: string,contracto:string): void {
    const byteCharacters = atob(base64String);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
      const slice = byteCharacters.slice(offset, offset + 512);
      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = nombre+" "+contracto+'.pdf';
    link.click();
    window.URL.revokeObjectURL(url);
  }
}
