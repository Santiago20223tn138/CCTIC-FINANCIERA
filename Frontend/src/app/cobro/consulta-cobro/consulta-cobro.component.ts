import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-consulta-cobro',
  templateUrl: './consulta-cobro.component.html',
  styleUrls: ['./consulta-cobro.component.css']
})
export class ConsultaCobroCortesComponent  implements OnInit{
  cortes = [];
  searchText: any;
  page!: number;
  id!: number;
  uri;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private activateRoute: ActivatedRoute) {
    this.uri = Propiedades.URL;
  }

  ngOnInit() {
    const token = localStorage.getItem('token');
    console.log(this.uri);
    
    if (!token) {
      this.router.navigate(['/login']);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.http.get<any>(this.uri+'financiera/Corte/CortePago/', { headers }).subscribe(data => {
      
      data.data.forEach((corte: { fecha: string; }) => {
        corte.fecha = corte.fecha.substring(0,10);
      });

      this.cortes = data.data;
      console.log(this.cortes);
      
    });
  }

  corteDetalle(id: any){
    this.router.navigate(['Consultar/Corte/', id]);
   }
}
