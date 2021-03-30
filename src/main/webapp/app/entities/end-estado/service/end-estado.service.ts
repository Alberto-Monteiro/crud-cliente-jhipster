import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEndEstado, getEndEstadoIdentifier } from '../end-estado.model';

export type EntityResponseType = HttpResponse<IEndEstado>;
export type EntityArrayResponseType = HttpResponse<IEndEstado[]>;

@Injectable({ providedIn: 'root' })
export class EndEstadoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/end-estados');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(endEstado: IEndEstado): Observable<EntityResponseType> {
    return this.http.post<IEndEstado>(this.resourceUrl, endEstado, { observe: 'response' });
  }

  update(endEstado: IEndEstado): Observable<EntityResponseType> {
    return this.http.put<IEndEstado>(`${this.resourceUrl}/${getEndEstadoIdentifier(endEstado) as number}`, endEstado, {
      observe: 'response',
    });
  }

  partialUpdate(endEstado: IEndEstado): Observable<EntityResponseType> {
    return this.http.patch<IEndEstado>(`${this.resourceUrl}/${getEndEstadoIdentifier(endEstado) as number}`, endEstado, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEndEstado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndEstado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEndEstadoToCollectionIfMissing(
    endEstadoCollection: IEndEstado[],
    ...endEstadosToCheck: (IEndEstado | null | undefined)[]
  ): IEndEstado[] {
    const endEstados: IEndEstado[] = endEstadosToCheck.filter(isPresent);
    if (endEstados.length > 0) {
      const endEstadoCollectionIdentifiers = endEstadoCollection.map(endEstadoItem => getEndEstadoIdentifier(endEstadoItem)!);
      const endEstadosToAdd = endEstados.filter(endEstadoItem => {
        const endEstadoIdentifier = getEndEstadoIdentifier(endEstadoItem);
        if (endEstadoIdentifier == null || endEstadoCollectionIdentifiers.includes(endEstadoIdentifier)) {
          return false;
        }
        endEstadoCollectionIdentifiers.push(endEstadoIdentifier);
        return true;
      });
      return [...endEstadosToAdd, ...endEstadoCollection];
    }
    return endEstadoCollection;
  }
}
