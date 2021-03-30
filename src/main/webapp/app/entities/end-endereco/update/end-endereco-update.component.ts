import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEndEndereco, EndEndereco } from '../end-endereco.model';
import { EndEnderecoService } from '../service/end-endereco.service';
import { IEndEstado } from 'app/entities/end-estado/end-estado.model';
import { EndEstadoService } from 'app/entities/end-estado/service/end-estado.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'cc-end-endereco-update',
  templateUrl: './end-endereco-update.component.html',
})
export class EndEnderecoUpdateComponent implements OnInit {
  isSaving = false;

  endEstadosSharedCollection: IEndEstado[] = [];
  clientesSharedCollection: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    nomeParaOEndereco: [null, [Validators.required, Validators.maxLength(100)]],
    cep: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
    cidade: [null, [Validators.required, Validators.maxLength(100)]],
    bairro: [null, [Validators.maxLength(100)]],
    logradouro: [null, [Validators.required, Validators.maxLength(256)]],
    numero: [null, [Validators.maxLength(30)]],
    complemento: [null, [Validators.maxLength(100)]],
    referencia: [null, [Validators.maxLength(100)]],
    estado: [],
    cliente: [],
  });

  constructor(
    protected endEnderecoService: EndEnderecoService,
    protected endEstadoService: EndEstadoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endEndereco }) => {
      this.updateForm(endEndereco);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endEndereco = this.createFromForm();
    if (endEndereco.id !== undefined) {
      this.subscribeToSaveResponse(this.endEnderecoService.update(endEndereco));
    } else {
      this.subscribeToSaveResponse(this.endEnderecoService.create(endEndereco));
    }
  }

  trackEndEstadoById(index: number, item: IEndEstado): number {
    return item.id!;
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndEndereco>>): void {
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

  protected updateForm(endEndereco: IEndEndereco): void {
    this.editForm.patchValue({
      id: endEndereco.id,
      nomeParaOEndereco: endEndereco.nomeParaOEndereco,
      cep: endEndereco.cep,
      cidade: endEndereco.cidade,
      bairro: endEndereco.bairro,
      logradouro: endEndereco.logradouro,
      numero: endEndereco.numero,
      complemento: endEndereco.complemento,
      referencia: endEndereco.referencia,
      estado: endEndereco.estado,
      cliente: endEndereco.cliente,
    });

    this.endEstadosSharedCollection = this.endEstadoService.addEndEstadoToCollectionIfMissing(
      this.endEstadosSharedCollection,
      endEndereco.estado
    );
    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, endEndereco.cliente);
  }

  protected loadRelationshipsOptions(): void {
    this.endEstadoService
      .query()
      .pipe(map((res: HttpResponse<IEndEstado[]>) => res.body ?? []))
      .pipe(
        map((endEstados: IEndEstado[]) =>
          this.endEstadoService.addEndEstadoToCollectionIfMissing(endEstados, this.editForm.get('estado')!.value)
        )
      )
      .subscribe((endEstados: IEndEstado[]) => (this.endEstadosSharedCollection = endEstados));

    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));
  }

  protected createFromForm(): IEndEndereco {
    return {
      ...new EndEndereco(),
      id: this.editForm.get(['id'])!.value,
      nomeParaOEndereco: this.editForm.get(['nomeParaOEndereco'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      referencia: this.editForm.get(['referencia'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }
}
