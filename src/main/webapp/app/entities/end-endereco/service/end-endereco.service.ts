import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEndEndereco, getEndEnderecoIdentifier } from '../end-endereco.model';

export type EntityResponseType = HttpResponse<IEndEndereco>;
export type EntityArrayResponseType = HttpResponse<IEndEndereco[]>;

@Injectable({ providedIn: 'root' })
export class EndEnderecoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/end-enderecos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(endEndereco: IEndEndereco): Observable<EntityResponseType> {
    return this.http.post<IEndEndereco>(this.resourceUrl, endEndereco, { observe: 'response' });
  }

  update(endEndereco: IEndEndereco): Observable<EntityResponseType> {
    return this.http.put<IEndEndereco>(`${this.resourceUrl}/${getEndEnderecoIdentifier(endEndereco) as number}`, endEndereco, {
      observe: 'response',
    });
  }

  partialUpdate(endEndereco: IEndEndereco): Observable<EntityResponseType> {
    return this.http.patch<IEndEndereco>(`${this.resourceUrl}/${getEndEnderecoIdentifier(endEndereco) as number}`, endEndereco, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEndEndereco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndEndereco[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEndEnderecoToCollectionIfMissing(
    endEnderecoCollection: IEndEndereco[],
    ...endEnderecosToCheck: (IEndEndereco | null | undefined)[]
  ): IEndEndereco[] {
    const endEnderecos: IEndEndereco[] = endEnderecosToCheck.filter(isPresent);
    if (endEnderecos.length > 0) {
      const endEnderecoCollectionIdentifiers = endEnderecoCollection.map(endEnderecoItem => getEndEnderecoIdentifier(endEnderecoItem)!);
      const endEnderecosToAdd = endEnderecos.filter(endEnderecoItem => {
        const endEnderecoIdentifier = getEndEnderecoIdentifier(endEnderecoItem);
        if (endEnderecoIdentifier == null || endEnderecoCollectionIdentifiers.includes(endEnderecoIdentifier)) {
          return false;
        }
        endEnderecoCollectionIdentifiers.push(endEnderecoIdentifier);
        return true;
      });
      return [...endEnderecosToAdd, ...endEnderecoCollection];
    }
    return endEnderecoCollection;
  }
}
