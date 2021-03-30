import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEndEstado, EndEstado } from '../end-estado.model';

import { EndEstadoService } from './end-estado.service';

describe('Service Tests', () => {
  describe('EndEstado Service', () => {
    let service: EndEstadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEndEstado;
    let expectedResult: IEndEstado | IEndEstado[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EndEstadoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        uf: 'AAAAAAA',
        descricao: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EndEstado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EndEstado()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EndEstado', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            uf: 'BBBBBB',
            descricao: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EndEstado', () => {
        const patchObject = Object.assign({}, new EndEstado());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EndEstado', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            uf: 'BBBBBB',
            descricao: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EndEstado', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEndEstadoToCollectionIfMissing', () => {
        it('should add a EndEstado to an empty array', () => {
          const endEstado: IEndEstado = { id: 123 };
          expectedResult = service.addEndEstadoToCollectionIfMissing([], endEstado);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(endEstado);
        });

        it('should not add a EndEstado to an array that contains it', () => {
          const endEstado: IEndEstado = { id: 123 };
          const endEstadoCollection: IEndEstado[] = [
            {
              ...endEstado,
            },
            { id: 456 },
          ];
          expectedResult = service.addEndEstadoToCollectionIfMissing(endEstadoCollection, endEstado);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EndEstado to an array that doesn't contain it", () => {
          const endEstado: IEndEstado = { id: 123 };
          const endEstadoCollection: IEndEstado[] = [{ id: 456 }];
          expectedResult = service.addEndEstadoToCollectionIfMissing(endEstadoCollection, endEstado);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(endEstado);
        });

        it('should add only unique EndEstado to an array', () => {
          const endEstadoArray: IEndEstado[] = [{ id: 123 }, { id: 456 }, { id: 3111 }];
          const endEstadoCollection: IEndEstado[] = [{ id: 123 }];
          expectedResult = service.addEndEstadoToCollectionIfMissing(endEstadoCollection, ...endEstadoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const endEstado: IEndEstado = { id: 123 };
          const endEstado2: IEndEstado = { id: 456 };
          expectedResult = service.addEndEstadoToCollectionIfMissing([], endEstado, endEstado2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(endEstado);
          expect(expectedResult).toContain(endEstado2);
        });

        it('should accept null and undefined values', () => {
          const endEstado: IEndEstado = { id: 123 };
          expectedResult = service.addEndEstadoToCollectionIfMissing([], null, endEstado, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(endEstado);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
