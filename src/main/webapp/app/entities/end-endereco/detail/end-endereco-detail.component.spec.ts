import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EndEnderecoDetailComponent } from './end-endereco-detail.component';

describe('Component Tests', () => {
  describe('EndEndereco Management Detail Component', () => {
    let comp: EndEnderecoDetailComponent;
    let fixture: ComponentFixture<EndEnderecoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EndEnderecoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ endEndereco: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EndEnderecoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndEnderecoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load endEndereco on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.endEndereco).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
