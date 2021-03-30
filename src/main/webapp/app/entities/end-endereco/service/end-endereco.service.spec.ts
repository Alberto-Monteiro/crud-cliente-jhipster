import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEndEndereco, EndEndereco } from '../end-endereco.model';

import { EndEnderecoService } from './end-endereco.service';

describe('Service Tests', () => {
  describe('EndEndereco Service', () => {
    let service: EndEnderecoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEndEndereco;
    let expectedResult: IEndEndereco | IEndEndereco[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EndEnderecoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nomeParaOEndereco: 'AAAAAAA',
        cep: 'AAAAAAA',
        cidade: 'AAAAAAA',
        bairro: 'AAAAAAA',
        logradouro: 'AAAAAAA',
        numero: 'AAAAAAA',
        complemento: 'AAAAAAA',
        referencia: 'AAAAAAA',
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

      it('should create a EndEndereco', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EndEndereco()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EndEndereco', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomeParaOEndereco: 'BBBBBB',
            cep: 'BBBBBB',
            cidade: 'BBBBBB',
            bairro: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            referencia: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EndEndereco', () => {
        const patchObject = Object.assign(
          {
            nomeParaOEndereco: 'BBBBBB',
            cidade: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            referencia: 'BBBBBB',
          },
          new EndEndereco()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EndEndereco', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomeParaOEndereco: 'BBBBBB',
            cep: 'BBBBBB',
            cidade: 'BBBBBB',
            bairro: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            referencia: 'BBBBBB',
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

      it('should delete a EndEndereco', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEndEnderecoToCollectionIfMissing', () => {
        it('should add a EndEndereco to an empty array', () => {
          const endEndereco: IEndEndereco = { id: 123 };
          expectedResult = service.addEndEnderecoToCollectionIfMissing([], endEndereco);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(endEndereco);
        });

        it('should not add a EndEndereco to an array that contains it', () => {
          const endEndereco: IEndEndereco = { id: 123 };
          const endEnderecoCollection: IEndEndereco[] = [
            {
              ...endEndereco,
            },
            { id: 456 },
          ];
          expectedResult = service.addEndEnderecoToCollectionIfMissing(endEnderecoCollection, endEndereco);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EndEndereco to an array that doesn't contain it", () => {
          const endEndereco: IEndEndereco = { id: 123 };
          const endEnderecoCollection: IEndEndereco[] = [{ id: 456 }];
          expectedResult = service.addEndEnderecoToCollectionIfMissing(endEnderecoCollection, endEndereco);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(endEndereco);
        });

        it('should add only unique EndEndereco to an array', () => {
          const endEnderecoArray: IEndEndereco[] = [{ id: 123 }, { id: 456 }, { id: 25688 }];
          const endEnderecoCollection: IEndEndereco[] = [{ id: 123 }];
          expectedResult = service.addEndEnderecoToCollectionIfMissing(endEnderecoCollection, ...endEnderecoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const endEndereco: IEndEndereco = { id: 123 };
          const endEndereco2: IEndEndereco = { id: 456 };
          expectedResult = service.addEndEnderecoToCollectionIfMissing([], endEndereco, endEndereco2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(endEndereco);
          expect(expectedResult).toContain(endEndereco2);
        });

        it('should accept null and undefined values', () => {
          const endEndereco: IEndEndereco = { id: 123 };
          expectedResult = service.addEndEnderecoToCollectionIfMissing([], null, endEndereco, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(endEndereco);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
