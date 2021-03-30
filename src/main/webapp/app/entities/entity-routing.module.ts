import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        data: { pageTitle: 'crudclienteApp.cliente.home.title' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'end-endereco',
        data: { pageTitle: 'crudclienteApp.endEndereco.home.title' },
        loadChildren: () => import('./end-endereco/end-endereco.module').then(m => m.EndEnderecoModule),
      },
      {
        path: 'end-estado',
        data: { pageTitle: 'crudclienteApp.endEstado.home.title' },
        loadChildren: () => import('./end-estado/end-estado.module').then(m => m.EndEstadoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
