import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEndEstado, EndEstado } from '../end-estado.model';
import { EndEstadoService } from '../service/end-estado.service';

@Component({
  selector: 'cc-end-estado-update',
  templateUrl: './end-estado-update.component.html',
})
export class EndEstadoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uf: [null, [Validators.required, Validators.maxLength(2)]],
    descricao: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected endEstadoService: EndEstadoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endEstado }) => {
      this.updateForm(endEstado);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endEstado = this.createFromForm();
    if (endEstado.id !== undefined) {
      this.subscribeToSaveResponse(this.endEstadoService.update(endEstado));
    } else {
      this.subscribeToSaveResponse(this.endEstadoService.create(endEstado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndEstado>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(endEstado: IEndEstado): void {
    this.editForm.patchValue({
      id: endEstado.id,
      uf: endEstado.uf,
      descricao: endEstado.descricao,
    });
  }

  protected createFromForm(): IEndEstado {
    return {
      ...new EndEstado(),
      id: this.editForm.get(['id'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
    };
  }
}
