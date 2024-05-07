import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { LandingComponent } from './landing/landing.component';
import { RegisterCustomerComponent } from './customer/register/register/register.component';
import { ConsultaClienteComponent } from './customer/consulta/consulta.component';
import { ConsultaComponent } from './customer/credito/consulta/consulta.component';
import { ConsultaCobroComponent } from './cobro/consulta/consulta.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { ConsultaCortoComponent } from './corte/consulta/consulta.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ConsultaCobroCortesComponent } from './cobro/consulta-cobro/consulta-cobro.component';
import { CommonModule } from '@angular/common';
import { DetallesConsultaComponent } from './corte/detalles-consulta/detalles-consulta.component';
import { ConsultarUsuariosComponent } from './usuarios/consultar-usuarios/consultar-usuarios.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LandingComponent,
    RegisterCustomerComponent,
    ConsultaClienteComponent,
    ConsultaComponent,
    ConsultaCobroComponent,
    SidebarComponent,
    ConsultaCortoComponent,
    ConsultaCobroCortesComponent,
    DetallesConsultaComponent,
    ConsultarUsuariosComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule, 
    MatIconModule, 
    HttpClientModule,
    Ng2SearchPipeModule,
    NgxPaginationModule,
    CommonModule,
  ],
  exports:[
     SidebarComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
