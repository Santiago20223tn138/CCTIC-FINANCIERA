import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { CorteModel } from './CorteModel';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-consulta',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css']
})
export class ConsultaCortoComponent implements OnInit{
  cortes: CorteModel[] = [];
  MontodeCorte = 0;
  Fecha: string | undefined ;
  searchText: any;
  page!: number;
  uri;
  id: number | undefined ;
  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private activateRoute: ActivatedRoute) {
    this.uri = Propiedades.URL;
    this.id = + activateRoute.snapshot.paramMap.get('id')!;
  }

  ngOnInit() {

    const token = localStorage.getItem('token');
    const hoy = new Date();
    const year = hoy.getFullYear();
    const month = ('0' + (hoy.getMonth() + 1)).slice(-2); // Agrega un cero adelante si es necesario
    const day = ('0' + hoy.getDate()).slice(-2);
    const fechaFormateada = `${year}-${month}-${day}`;

    this.Fecha= fechaFormateada;

    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.post<any>(this.uri+'financiera/credito/CorteDePago/'+this.id, {},{ headers }).subscribe(data => {
      this.cortes = data.data;
      this.MontodeCorte = this.cortes.reduce((acc, corte) => {
        if (corte && corte.monto !== undefined) {
          return acc + corte.monto;
        } else {
          return acc;
        }
      }, 0);
    });
  }
}
