import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EndEstadoComponent } from '../list/end-estado.component';
import { EndEstadoDetailComponent } from '../detail/end-estado-detail.component';
import { EndEstadoUpdateComponent } from '../update/end-estado-update.component';
import { EndEstadoRoutingResolveService } from './end-estado-routing-resolve.service';

const endEstadoRoute: Routes = [
  {
    path: '',
    component: EndEstadoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EndEstadoDetailComponent,
    resolve: {
      endEstado: EndEstadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EndEstadoUpdateComponent,
    resolve: {
      endEstado: EndEstadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EndEstadoUpdateComponent,
    resolve: {
      endEstado: EndEstadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(endEstadoRoute)],
  exports: [RouterModule],
})
export class EndEstadoRoutingModule {}
