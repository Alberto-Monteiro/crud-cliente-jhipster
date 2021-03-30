import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndEndereco } from '../end-endereco.model';

@Component({
  selector: 'cc-end-endereco-detail',
  templateUrl: './end-endereco-detail.component.html',
})
export class EndEnderecoDetailComponent implements OnInit {
  endEndereco: IEndEndereco | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endEndereco }) => {
      this.endEndereco = endEndereco;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
