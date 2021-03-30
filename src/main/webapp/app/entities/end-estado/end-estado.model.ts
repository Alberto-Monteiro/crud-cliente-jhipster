import { IEndEndereco } from 'app/entities/end-endereco/end-endereco.model';

export interface IEndEstado {
  id?: number;
  uf?: string;
  descricao?: string;
  enderecos?: IEndEndereco[] | null;
}

export class EndEstado implements IEndEstado {
  constructor(public id?: number, public uf?: string, public descricao?: string, public enderecos?: IEndEndereco[] | null) {}
}

export function getEndEstadoIdentifier(endEstado: IEndEstado): number | undefined {
  return endEstado.id;
}
