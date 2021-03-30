import { element, by, ElementFinder } from 'protractor';

export class EndEstadoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('cc-end-estado div table .btn-danger'));
  title = element.all(by.css('cc-end-estado div h2#page-heading span')).first();
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

export class EndEstadoUpdatePage {
  pageTitle = element(by.id('cc-end-estado-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  ufInput = element(by.id('field_uf'));
  descricaoInput = element(by.id('field_descricao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('ccTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setUfInput(uf: string): Promise<void> {
    await this.ufInput.sendKeys(uf);
  }

  async getUfInput(): Promise<string> {
    return await this.ufInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
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

export class EndEstadoDeleteDialog {
  private dialogTitle = element(by.id('cc-delete-endEstado-heading'));
  private confirmButton = element(by.id('cc-confirm-delete-endEstado'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('ccTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
