jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EndEstadoService } from '../service/end-estado.service';
import { IEndEstado, EndEstado } from '../end-estado.model';

import { EndEstadoUpdateComponent } from './end-estado-update.component';

describe('Component Tests', () => {
  describe('EndEstado Management Update Component', () => {
    let comp: EndEstadoUpdateComponent;
    let fixture: ComponentFixture<EndEstadoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let endEstadoService: EndEstadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EndEstadoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EndEstadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndEstadoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      endEstadoService = TestBed.inject(EndEstadoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const endEstado: IEndEstado = { id: 456 };

        activatedRoute.data = of({ endEstado });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(endEstado));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEstado = { id: 123 };
        spyOn(endEstadoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEstado });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: endEstado }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(endEstadoService.update).toHaveBeenCalledWith(endEstado);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEstado = new EndEstado();
        spyOn(endEstadoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEstado });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: endEstado }));
        saveSubject.complete();

        // THEN
        expect(endEstadoService.create).toHaveBeenCalledWith(endEstado);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEstado = { id: 123 };
        spyOn(endEstadoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEstado });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(endEstadoService.update).toHaveBeenCalledWith(endEstado);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
