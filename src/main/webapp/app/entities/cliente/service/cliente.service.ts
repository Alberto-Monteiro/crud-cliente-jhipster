import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getClienteIdentifier, ICliente } from '../cliente.model';

export type EntityResponseType = HttpResponse<ICliente>;
export type EntityArrayResponseType = HttpResponse<ICliente[]>;

@Injectable({ providedIn: 'root' })
export class ClienteService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/clientes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .post<ICliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .put<ICliente>(`${this.resourceUrl}/${getClienteIdentifier(cliente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .patch<ICliente>(`${this.resourceUrl}/${getClienteIdentifier(cliente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryComFiltro(req: any, filtroPesquisa: ICliente): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    const copy = this.convertDateFromClient(filtroPesquisa);
    return this.http
      .post<ICliente[]>(`${this.resourceUrl}/filtro`, copy, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addClienteToCollectionIfMissing(clienteCollection: ICliente[], ...clientesToCheck: (ICliente | null | undefined)[]): ICliente[] {
    const clientes: ICliente[] = clientesToCheck.filter(isPresent);
    if (clientes.length > 0) {
      const clienteCollectionIdentifiers = clienteCollection.map(clienteItem => getClienteIdentifier(clienteItem)!);
      const clientesToAdd = clientes.filter(clienteItem => {
        const clienteIdentifier = getClienteIdentifier(clienteItem);
        if (clienteIdentifier == null || clienteCollectionIdentifiers.includes(clienteIdentifier)) {
          return false;
        }
        clienteCollectionIdentifiers.push(clienteIdentifier);
        return true;
      });
      return [...clientesToAdd, ...clienteCollection];
    }
    return clienteCollection;
  }

  protected convertDateFromClient(cliente: ICliente): ICliente {
    return Object.assign({}, cliente, {
      dataNascimento: cliente.dataNascimento?.isValid() ? cliente.dataNascimento.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascimento = res.body.dataNascimento ? dayjs(res.body.dataNascimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cliente: ICliente) => {
        cliente.dataNascimento = cliente.dataNascimento ? dayjs(cliente.dataNascimento) : undefined;
      });
    }
    return res;
  }
}
