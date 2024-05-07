import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import * as XLSX from 'xlsx';
import swal from 'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';
import { AgenteModel } from './agente.model';

@Component({
  selector: 'app-ConsultaCobroComponent',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css']
})
export class ConsultaCobroComponent implements OnInit {

  lista: string[] = ["LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"
  ];
  seleccionado: string = ""; 
  seleccionadoRepatidor: string = ""; 
  lunes: string = "LUNES";
  montoDiaDepago: number = 0;
  base64: any;
  url : string | undefined;
  searchText: any;
  clientes = [];  
  agentes: AgenteModel[] = [];  

  hoy = new Date();
  name = 'Cobros semanal';
  year = this.hoy.getFullYear();
  month = ('0' + (this.hoy.getMonth() + 1)).slice(-2); // Agrega un cero adelante si es necesario
  day = ('0' + this.hoy.getDate()).slice(-2);
  pagoinicialseleccionado: number = 0;
  Idseleccionado: number = 0;
  page!: number;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient) {
    this.url = Propiedades.URL;
  }


  seleccionarElPago(monto: number, id: number) {
    this.pagoinicialseleccionado = monto;
    this.Idseleccionado = id;
  }

  ngOnInit() {
    const token = localStorage.getItem('token');
    const id = localStorage.getItem('id');
    console.log("dsa");

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.http.get<any>(this.url+'financiera/credito/consultaListaPorCobrar/'+id, { headers }).subscribe(data => {
      this.clientes = data.data;
      console.log(this.clientes);

    });
    const body ={
      "id": 2,
      "acronym": "Agente" ,
      "description": "Cobrador"
    }
    this.http.post<any>(this.url+'financiera/user/Repatidor/', body,{ headers }).subscribe(data => {
      this.agentes = data.data;
      console.log(this.agentes);

    });
  }

  HistorialCredtio(id: any) {
    this.router.navigate(['Consultar/Credito/', id]);
  }

  BuscarAgente(){
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    console.log(this.seleccionadoRepatidor);
    this.http.get<any>(this.url+'financiera/credito/consultaListaPorCobrar/'+this.seleccionadoRepatidor, { headers }).subscribe(data => {
      this.clientes = data.data;

    });
  }

  descargarExcel() {
    const fechaFormateada = `${this.year}-${this.month}-${this.day}`;

    let element = document.getElementById('Tabla');
    const worksheet: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);
    const wscols: XLSX.ColInfo[] = [
      { wch: 5 },
      { wch: 8 },
      { wch: 15 },
      { wch: 15 },
      { wch: 15 },
      { wch: 35 }, { wch: 15 }, { wch: 15 }, { wch: 15 }, { wch: 10 }, { wch: 10 }, { wch: 25 },
    ];
    const book: XLSX.WorkBook = XLSX.utils.book_new();

    XLSX.utils.book_append_sheet(book, worksheet, 'Sheet1');
    book.Sheets['Sheet1']['!cols'] = wscols;

    XLSX.writeFile(book, this.name + " " + fechaFormateada + ".xlsx");

  }

  registrarDiaDePago() {
    const token = localStorage.getItem('token');
    let id = 0;

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const fechaFormateada = `${this.year}-${this.month}-${this.day}`;

    this.http.put<any>(`${this.url}/client/credito/`, { diaPago: this.seleccionado, id: this.Idseleccionado }, { headers })
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

    this.http.post<any>(`${this.url}/credito/`, { fecha: fechaFormateada, monto: this.montoDiaDepago, credito: this.Idseleccionado }, { headers })
      .subscribe(
        (resp) => {
          id = resp.data.id;
          swal.fire({
            position: "center",
            icon: "success",
            title: "Se registro correctamente",
            showConfirmButton: false,
            timer: 1500
          });
          this.http.get<any>(`${this.url}/recibo-pago/pdf/` + resp.data.id, { headers })
            .subscribe(
              (data) => {
                console.log(data);
                this.base64 = data.data;
                this.downloadPdf(this.base64, "Recibo de Pago", "");
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

  downloadPdf(base64String: string, nombre: string, contracto: string): void {
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
    link.download = nombre + " " + contracto + '.pdf';
    link.click();
    window.URL.revokeObjectURL(url);
  }
  corte() {
    swal.fire({
      title: "¿Seguro de realizar el corte?",
      showDenyButton: true,
      showCancelButton: true,
      confirmButtonText: "Guardar",
      denyButtonText: `No realizar corte`
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        this.router.navigate(['/Consultar/Cortes/'+this.seleccionadoRepatidor]);
      } else if (result.isDenied) {
        swal.fire("Se cancelo el corte", "", "info");
      }
    });
  }
}
