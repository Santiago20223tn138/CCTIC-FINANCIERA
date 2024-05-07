import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import swal from 'sweetalert2';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-detalles-consulta',
  templateUrl: './detalles-consulta.component.html',
  styleUrls: ['./detalles-consulta.component.css']
})
export class DetallesConsultaComponent  implements OnInit{
  cortes = [];
  searchText: any;
  page!: number;
  id!: number;
  hoy = new Date();
  base64: any;
  uri;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private activateRoute: ActivatedRoute) {
    this.id = + activateRoute.snapshot.paramMap.get('id')!;
    this.uri = Propiedades.URL;

  }

  ngOnInit() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.http.get<any>(this.uri+'financiera/Corte/getByIdHistoricoCorteDePagosDetalles/'+this.id, { headers }).subscribe(data => {

      this.cortes = data.data;
      console.log(this.cortes);
      
    });
  }

  descagar(contracto :string) {
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

    this.http.get<any>(this.uri+'financiera/cortePago-info/pdf/' + this.id, { headers }).subscribe(data => {
      this.base64 = data.data;
      this.downloadPdf(this.base64,"Historico",contracto); // Move this line inside the subscribe block
    });

    swal.close;
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
