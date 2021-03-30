import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEndEstado } from '../end-estado.model';
import { EndEstadoService } from '../service/end-estado.service';

@Component({
  templateUrl: './end-estado-delete-dialog.component.html',
})
export class EndEstadoDeleteDialogComponent {
  endEstado?: IEndEstado;

  constructor(protected endEstadoService: EndEstadoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.endEstadoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
