import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaCobroCortesComponent } from './consulta-cobro.component';

describe('ConsultaCobroCortesComponent', () => {
  let component: ConsultaCobroCortesComponent;
  let fixture: ComponentFixture<ConsultaCobroCortesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultaCobroCortesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultaCobroCortesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
