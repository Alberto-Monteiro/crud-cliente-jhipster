import * as dayjs from 'dayjs';
import { IEndEndereco } from 'app/entities/end-endereco/end-endereco.model';
import { Sexo } from 'app/entities/enumerations/sexo.model';

export interface ICliente {
  id?: number;
  cpf?: string;
  nome?: string;
  sobrenome?: string;
  dataNascimento?: dayjs.Dayjs;
  telefone?: string;
  email?: string;
  sexo?: Sexo;
  enderecos?: IEndEndereco[] | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public cpf?: string,
    public nome?: string,
    public sobrenome?: string,
    public dataNascimento?: dayjs.Dayjs,
    public telefone?: string,
    public email?: string,
    public sexo?: Sexo,
    public enderecos?: IEndEndereco[] | null
  ) {}
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
