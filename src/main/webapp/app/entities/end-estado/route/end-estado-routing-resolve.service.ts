import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEndEstado, EndEstado } from '../end-estado.model';
import { EndEstadoService } from '../service/end-estado.service';

@Injectable({ providedIn: 'root' })
export class EndEstadoRoutingResolveService implements Resolve<IEndEstado> {
  constructor(protected service: EndEstadoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEndEstado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((endEstado: HttpResponse<EndEstado>) => {
          if (endEstado.body) {
            return of(endEstado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EndEstado());
  }
}
