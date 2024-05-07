import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-consulta',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css']
})

export class ConsultaClienteComponent implements OnInit{
  searchText: any;
  clientes = [];
  uri;
  page!: number;
  constructor(private authService: LoginService, private router: Router, private http: HttpClient) {
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
    this.http.get<any>(this.uri+'financiera/client/', { headers }).subscribe(data => {
      this.clientes = data.data;
      
    });

  }
  redirectToRegisterCustomer() {
    this.router.navigate(['Registar/Cliente']);
  }

  HistorialCredtio(id : any){
    this.router.navigate(['Consultar/Credito/', id]);

  }
}
