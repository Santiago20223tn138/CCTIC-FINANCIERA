import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallesConsultaComponent } from './detalles-consulta.component';

describe('DetallesConsultaComponent', () => {
  let component: DetallesConsultaComponent;
  let fixture: ComponentFixture<DetallesConsultaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetallesConsultaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallesConsultaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
