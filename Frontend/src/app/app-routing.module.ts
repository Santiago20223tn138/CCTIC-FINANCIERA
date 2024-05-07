import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LandingComponent } from './landing/landing.component';
import { RegisterCustomerComponent } from './customer/register/register/register.component';
import { ConsultaClienteComponent } from './customer/consulta/consulta.component';
import { ConsultaComponent } from './customer/credito/consulta/consulta.component';
import { ConsultaCobroComponent } from './cobro/consulta/consulta.component';
import { ConsultaCortoComponent } from './corte/consulta/consulta.component';
import { ConsultaCobroCortesComponent } from './cobro/consulta-cobro/consulta-cobro.component';
import { DetallesConsultaComponent } from './corte/detalles-consulta/detalles-consulta.component';
import { ConsultarUsuariosComponent } from './usuarios/consultar-usuarios/consultar-usuarios.component';


const routes: Routes = [
  { path: '',  component: LoginComponent },
  {path:"login", component: LoginComponent},
  { path: 'landing', component: LandingComponent },
  { path: 'Registar/Cliente', component: RegisterCustomerComponent },
  { path: 'Consultar/Cliente', component: ConsultaClienteComponent },
  { path: 'Consultar/Credito/:id', component: ConsultaComponent },
  { path: 'Consultar/Cobros', component: ConsultaCobroComponent },
  { path: 'Consultar/Cortes/:id', component: ConsultaCortoComponent },
  { path: 'Consultar/ConsultaCobroCortes', component: ConsultaCobroCortesComponent },
  { path: 'Consultar/Corte/:id', component: DetallesConsultaComponent },
  { path: 'Consultar/Usuarios', component: ConsultarUsuariosComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
