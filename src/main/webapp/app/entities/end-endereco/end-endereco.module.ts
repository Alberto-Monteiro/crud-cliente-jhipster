import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EndEnderecoComponent } from './list/end-endereco.component';
import { EndEnderecoDetailComponent } from './detail/end-endereco-detail.component';
import { EndEnderecoUpdateComponent } from './update/end-endereco-update.component';
import { EndEnderecoDeleteDialogComponent } from './delete/end-endereco-delete-dialog.component';
import { EndEnderecoRoutingModule } from './route/end-endereco-routing.module';

@NgModule({
  imports: [SharedModule, EndEnderecoRoutingModule],
  declarations: [EndEnderecoComponent, EndEnderecoDetailComponent, EndEnderecoUpdateComponent, EndEnderecoDeleteDialogComponent],
  entryComponents: [EndEnderecoDeleteDialogComponent],
})
export class EndEnderecoModule {}
