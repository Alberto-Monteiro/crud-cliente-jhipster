import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('cc-cliente div table .btn-danger'));
  title = element.all(by.css('cc-cliente div h2#page-heading span')).first();
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

export class ClienteUpdatePage {
  pageTitle = element(by.id('cc-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cpfInput = element(by.id('field_cpf'));
  nomeInput = element(by.id('field_nome'));
  sobrenomeInput = element(by.id('field_sobrenome'));
  dataNascimentoInput = element(by.id('field_dataNascimento'));
  telefoneInput = element(by.id('field_telefone'));
  emailInput = element(by.id('field_email'));
  sexoSelect = element(by.id('field_sexo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('ccTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCpfInput(cpf: string): Promise<void> {
    await this.cpfInput.sendKeys(cpf);
  }

  async getCpfInput(): Promise<string> {
    return await this.cpfInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setSobrenomeInput(sobrenome: string): Promise<void> {
    await this.sobrenomeInput.sendKeys(sobrenome);
  }

  async getSobrenomeInput(): Promise<string> {
    return await this.sobrenomeInput.getAttribute('value');
  }

  async setDataNascimentoInput(dataNascimento: string): Promise<void> {
    await this.dataNascimentoInput.sendKeys(dataNascimento);
  }

  async getDataNascimentoInput(): Promise<string> {
    return await this.dataNascimentoInput.getAttribute('value');
  }

  async setTelefoneInput(telefone: string): Promise<void> {
    await this.telefoneInput.sendKeys(telefone);
  }

  async getTelefoneInput(): Promise<string> {
    return await this.telefoneInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setSexoSelect(sexo: string): Promise<void> {
    await this.sexoSelect.sendKeys(sexo);
  }

  async getSexoSelect(): Promise<string> {
    return await this.sexoSelect.element(by.css('option:checked')).getText();
  }

  async sexoSelectLastOption(): Promise<void> {
    await this.sexoSelect.all(by.tagName('option')).last().click();
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

export class ClienteDeleteDialog {
  private dialogTitle = element(by.id('cc-delete-cliente-heading'));
  private confirmButton = element(by.id('cc-confirm-delete-cliente'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('ccTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
