import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaCortoComponent } from './consulta.component';

describe('ConsultaCortoComponent', () => {
  let component: ConsultaCortoComponent;
  let fixture: ComponentFixture<ConsultaCortoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultaCortoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultaCortoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
