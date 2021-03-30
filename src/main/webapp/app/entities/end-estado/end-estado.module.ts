import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EndEstadoComponent } from './list/end-estado.component';
import { EndEstadoDetailComponent } from './detail/end-estado-detail.component';
import { EndEstadoUpdateComponent } from './update/end-estado-update.component';
import { EndEstadoDeleteDialogComponent } from './delete/end-estado-delete-dialog.component';
import { EndEstadoRoutingModule } from './route/end-estado-routing.module';

@NgModule({
  imports: [SharedModule, EndEstadoRoutingModule],
  declarations: [EndEstadoComponent, EndEstadoDetailComponent, EndEstadoUpdateComponent, EndEstadoDeleteDialogComponent],
  entryComponents: [EndEstadoDeleteDialogComponent],
})
export class EndEstadoModule {}
