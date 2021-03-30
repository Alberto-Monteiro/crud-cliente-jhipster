jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EndEnderecoService } from '../service/end-endereco.service';
import { IEndEndereco, EndEndereco } from '../end-endereco.model';
import { IEndEstado } from 'app/entities/end-estado/end-estado.model';
import { EndEstadoService } from 'app/entities/end-estado/service/end-estado.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { EndEnderecoUpdateComponent } from './end-endereco-update.component';

describe('Component Tests', () => {
  describe('EndEndereco Management Update Component', () => {
    let comp: EndEnderecoUpdateComponent;
    let fixture: ComponentFixture<EndEnderecoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let endEnderecoService: EndEnderecoService;
    let endEstadoService: EndEstadoService;
    let clienteService: ClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EndEnderecoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EndEnderecoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndEnderecoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      endEnderecoService = TestBed.inject(EndEnderecoService);
      endEstadoService = TestBed.inject(EndEstadoService);
      clienteService = TestBed.inject(ClienteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call EndEstado query and add missing value', () => {
        const endEndereco: IEndEndereco = { id: 456 };
        const estado: IEndEstado = { id: 3446 };
        endEndereco.estado = estado;

        const endEstadoCollection: IEndEstado[] = [{ id: 61082 }];
        spyOn(endEstadoService, 'query').and.returnValue(of(new HttpResponse({ body: endEstadoCollection })));
        const additionalEndEstados = [estado];
        const expectedCollection: IEndEstado[] = [...additionalEndEstados, ...endEstadoCollection];
        spyOn(endEstadoService, 'addEndEstadoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        expect(endEstadoService.query).toHaveBeenCalled();
        expect(endEstadoService.addEndEstadoToCollectionIfMissing).toHaveBeenCalledWith(endEstadoCollection, ...additionalEndEstados);
        expect(comp.endEstadosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cliente query and add missing value', () => {
        const endEndereco: IEndEndereco = { id: 456 };
        const cliente: ICliente = { id: 69912 };
        endEndereco.cliente = cliente;

        const clienteCollection: ICliente[] = [{ id: 50552 }];
        spyOn(clienteService, 'query').and.returnValue(of(new HttpResponse({ body: clienteCollection })));
        const additionalClientes = [cliente];
        const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
        spyOn(clienteService, 'addClienteToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        expect(clienteService.query).toHaveBeenCalled();
        expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, ...additionalClientes);
        expect(comp.clientesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const endEndereco: IEndEndereco = { id: 456 };
        const estado: IEndEstado = { id: 94913 };
        endEndereco.estado = estado;
        const cliente: ICliente = { id: 30993 };
        endEndereco.cliente = cliente;

        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(endEndereco));
        expect(comp.endEstadosSharedCollection).toContain(estado);
        expect(comp.clientesSharedCollection).toContain(cliente);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEndereco = { id: 123 };
        spyOn(endEnderecoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: endEndereco }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(endEnderecoService.update).toHaveBeenCalledWith(endEndereco);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEndereco = new EndEndereco();
        spyOn(endEnderecoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: endEndereco }));
        saveSubject.complete();

        // THEN
        expect(endEnderecoService.create).toHaveBeenCalledWith(endEndereco);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const endEndereco = { id: 123 };
        spyOn(endEnderecoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ endEndereco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(endEnderecoService.update).toHaveBeenCalledWith(endEndereco);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEndEstadoById', () => {
        it('Should return tracked EndEstado primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEndEstadoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackClienteById', () => {
        it('Should return tracked Cliente primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackClienteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
