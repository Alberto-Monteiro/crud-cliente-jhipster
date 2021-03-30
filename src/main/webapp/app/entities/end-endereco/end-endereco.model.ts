import { IEndEstado } from 'app/entities/end-estado/end-estado.model';
import { ICliente } from 'app/entities/cliente/cliente.model';

export interface IEndEndereco {
  id?: number;
  nomeParaOEndereco?: string;
  cep?: string;
  cidade?: string;
  bairro?: string | null;
  logradouro?: string;
  numero?: string | null;
  complemento?: string | null;
  referencia?: string | null;
  estado?: IEndEstado | null;
  cliente?: ICliente | null;
}

export class EndEndereco implements IEndEndereco {
  constructor(
    public id?: number,
    public nomeParaOEndereco?: string,
    public cep?: string,
    public cidade?: string,
    public bairro?: string | null,
    public logradouro?: string,
    public numero?: string | null,
    public complemento?: string | null,
    public referencia?: string | null,
    public estado?: IEndEstado | null,
    public cliente?: ICliente | null
  ) {}
}

export function getEndEnderecoIdentifier(endEndereco: IEndEndereco): number | undefined {
  return endEndereco.id;
}
