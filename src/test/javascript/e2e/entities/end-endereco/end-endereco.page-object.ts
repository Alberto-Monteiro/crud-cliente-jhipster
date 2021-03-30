import { element, by, ElementFinder } from 'protractor';

export class EndEnderecoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('cc-end-endereco div table .btn-danger'));
  title = element.all(by.css('cc-end-endereco div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('ccTranslate');
  }
}

export class EndEnderecoUpdatePage {
  pageTitle = element(by.id('cc-end-endereco-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  nomeParaOEnderecoInput = element(by.id('field_nomeParaOEndereco'));
  cepInput = element(by.id('field_cep'));
  cidadeInput = element(by.id('field_cidade'));
  bairroInput = element(by.id('field_bairro'));
  logradouroInput = element(by.id('field_logradouro'));
  numeroInput = element(by.id('field_numero'));
  complementoInput = element(by.id('field_complemento'));
  referenciaInput = element(by.id('field_referencia'));

  estadoSelect = element(by.id('field_estado'));
  clienteSelect = element(by.id('field_cliente'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('ccTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setNomeParaOEnderecoInput(nomeParaOEndereco: string): Promise<void> {
    await this.nomeParaOEnderecoInput.sendKeys(nomeParaOEndereco);
  }

  async getNomeParaOEnderecoInput(): Promise<string> {
    return await this.nomeParaOEnderecoInput.getAttribute('value');
  }

  async setCepInput(cep: string): Promise<void> {
    await this.cepInput.sendKeys(cep);
  }

  async getCepInput(): Promise<string> {
    return await this.cepInput.getAttribute('value');
  }

  async setCidadeInput(cidade: string): Promise<void> {
    await this.cidadeInput.sendKeys(cidade);
  }

  async getCidadeInput(): Promise<string> {
    return await this.cidadeInput.getAttribute('value');
  }

  async setBairroInput(bairro: string): Promise<void> {
    await this.bairroInput.sendKeys(bairro);
  }

  async getBairroInput(): Promise<string> {
    return await this.bairroInput.getAttribute('value');
  }

  async setLogradouroInput(logradouro: string): Promise<void> {
    await this.logradouroInput.sendKeys(logradouro);
  }

  async getLogradouroInput(): Promise<string> {
    return await this.logradouroInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  async setComplementoInput(complemento: string): Promise<void> {
    await this.complementoInput.sendKeys(complemento);
  }

  async getComplementoInput(): Promise<string> {
    return await this.complementoInput.getAttribute('value');
  }

  async setReferenciaInput(referencia: string): Promise<void> {
    await this.referenciaInput.sendKeys(referencia);
  }

  async getReferenciaInput(): Promise<string> {
    return await this.referenciaInput.getAttribute('value');
  }

  async estadoSelectLastOption(): Promise<void> {
    await this.estadoSelect.all(by.tagName('option')).last().click();
  }

  async estadoSelectOption(option: string): Promise<void> {
    await this.estadoSelect.sendKeys(option);
  }

  getEstadoSelect(): ElementFinder {
    return this.estadoSelect;
  }

  async getEstadoSelectedOption(): Promise<string> {
    return await this.estadoSelect.element(by.css('option:checked')).getText();
  }

  async clienteSelectLastOption(): Promise<void> {
    await this.clienteSelect.all(by.tagName('option')).last().click();
  }

  async clienteSelectOption(option: string): Promise<void> {
    await this.clienteSelect.sendKeys(option);
  }

  getClienteSelect(): ElementFinder {
    return this.clienteSelect;
  }

  async getClienteSelectedOption(): Promise<string> {
    return await this.clienteSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EndEnderecoDeleteDialog {
  private dialogTitle = element(by.id('cc-delete-endEndereco-heading'));
  private confirmButton = element(by.id('cc-confirm-delete-endEndereco'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('ccTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
