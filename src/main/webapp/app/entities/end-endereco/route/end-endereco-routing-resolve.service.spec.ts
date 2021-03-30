jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEndEndereco, EndEndereco } from '../end-endereco.model';
import { EndEnderecoService } from '../service/end-endereco.service';

import { EndEnderecoRoutingResolveService } from './end-endereco-routing-resolve.service';

describe('Service Tests', () => {
  describe('EndEndereco routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EndEnderecoRoutingResolveService;
    let service: EndEnderecoService;
    let resultEndEndereco: IEndEndereco | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EndEnderecoRoutingResolveService);
      service = TestBed.inject(EndEnderecoService);
      resultEndEndereco = undefined;
    });

    describe('resolve', () => {
      it('should return IEndEndereco returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEndEndereco = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEndEndereco).toEqual({ id: 123 });
      });

      it('should return new IEndEndereco if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEndEndereco = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEndEndereco).toEqual(new EndEndereco());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEndEndereco = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEndEndereco).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
