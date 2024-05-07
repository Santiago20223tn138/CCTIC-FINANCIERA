import { Component, AfterViewInit } from '@angular/core';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';
import { ContractoService } from '../service/contracto/contracto.service';
import { HttpClient } from '@angular/common/http';
import { Propiedades } from 'src/app/shared/model/propiedades';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {
  private primeraRecarga = true;

  constructor(private authService: LoginService, private router: Router, private http: HttpClient, private contracto: ContractoService) {
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

  
  ngAfterViewInit() {
    if(Propiedades.flag == true){
      window.location.reload();
    };
  }

}
