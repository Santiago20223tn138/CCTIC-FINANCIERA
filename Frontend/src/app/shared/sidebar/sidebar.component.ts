import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  contador = 0;

 hidden = true;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private activateRoute: ActivatedRoute) {
    this.contador = 0;
  }
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if(this.contador != 0){
      window.location.reload();
      this.contador++;
    }
    if (!token) {
      this.hidden =false;
      this.router.navigate(['/login']);
      
    }else{
      this.hidden = true;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

  }

  logout() {
    localStorage.removeItem('token');
    Propiedades.flag = false;

    this.router.navigate(['/login']);
  }

}
