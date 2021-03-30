import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndEstado } from '../end-estado.model';

@Component({
  selector: 'cc-end-estado-detail',
  templateUrl: './end-estado-detail.component.html',
})
export class EndEstadoDetailComponent implements OnInit {
  endEstado: IEndEstado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endEstado }) => {
      this.endEstado = endEstado;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
