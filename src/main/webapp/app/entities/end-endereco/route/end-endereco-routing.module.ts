import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EndEnderecoComponent } from '../list/end-endereco.component';
import { EndEnderecoDetailComponent } from '../detail/end-endereco-detail.component';
import { EndEnderecoUpdateComponent } from '../update/end-endereco-update.component';
import { EndEnderecoRoutingResolveService } from './end-endereco-routing-resolve.service';

const endEnderecoRoute: Routes = [
  {
    path: '',
    component: EndEnderecoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EndEnderecoDetailComponent,
    resolve: {
      endEndereco: EndEnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EndEnderecoUpdateComponent,
    resolve: {
      endEndereco: EndEnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EndEnderecoUpdateComponent,
    resolve: {
      endEndereco: EndEnderecoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(endEnderecoRoute)],
  exports: [RouterModule],
})
export class EndEnderecoRoutingModule {}
