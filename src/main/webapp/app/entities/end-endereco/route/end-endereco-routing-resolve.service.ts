import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEndEndereco, EndEndereco } from '../end-endereco.model';
import { EndEnderecoService } from '../service/end-endereco.service';

@Injectable({ providedIn: 'root' })
export class EndEnderecoRoutingResolveService implements Resolve<IEndEndereco> {
  constructor(protected service: EndEnderecoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEndEndereco> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((endEndereco: HttpResponse<EndEndereco>) => {
          if (endEndereco.body) {
            return of(endEndereco.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EndEndereco());
  }
}
