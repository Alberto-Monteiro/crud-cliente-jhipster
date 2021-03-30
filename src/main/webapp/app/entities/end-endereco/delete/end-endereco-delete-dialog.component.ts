import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEndEndereco } from '../end-endereco.model';
import { EndEnderecoService } from '../service/end-endereco.service';

@Component({
  templateUrl: './end-endereco-delete-dialog.component.html',
})
export class EndEnderecoDeleteDialogComponent {
  endEndereco?: IEndEndereco;

  constructor(protected endEnderecoService: EndEnderecoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.endEnderecoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
