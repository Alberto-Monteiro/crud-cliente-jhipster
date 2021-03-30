import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EndEstadoDetailComponent } from './end-estado-detail.component';

describe('Component Tests', () => {
  describe('EndEstado Management Detail Component', () => {
    let comp: EndEstadoDetailComponent;
    let fixture: ComponentFixture<EndEstadoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EndEstadoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ endEstado: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EndEstadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndEstadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load endEstado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.endEstado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
