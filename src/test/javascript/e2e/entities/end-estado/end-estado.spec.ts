import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EndEstadoComponentsPage, EndEstadoDeleteDialog, EndEstadoUpdatePage } from './end-estado.page-object';

const expect = chai.expect;

describe('EndEstado e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let endEstadoComponentsPage: EndEstadoComponentsPage;
  let endEstadoUpdatePage: EndEstadoUpdatePage;
  let endEstadoDeleteDialog: EndEstadoDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EndEstados', async () => {
    await navBarPage.goToEntity('end-estado');
    endEstadoComponentsPage = new EndEstadoComponentsPage();
    await browser.wait(ec.visibilityOf(endEstadoComponentsPage.title), 5000);
    expect(await endEstadoComponentsPage.getTitle()).to.eq('crudclienteApp.endEstado.home.title');
    await browser.wait(ec.or(ec.visibilityOf(endEstadoComponentsPage.entities), ec.visibilityOf(endEstadoComponentsPage.noResult)), 1000);
  });

  it('should load create EndEstado page', async () => {
    await endEstadoComponentsPage.clickOnCreateButton();
    endEstadoUpdatePage = new EndEstadoUpdatePage();
    expect(await endEstadoUpdatePage.getPageTitle()).to.eq('crudclienteApp.endEstado.home.createOrEditLabel');
    await endEstadoUpdatePage.cancel();
  });

  it('should create and save EndEstados', async () => {
    const nbButtonsBeforeCreate = await endEstadoComponentsPage.countDeleteButtons();

    await endEstadoComponentsPage.clickOnCreateButton();

    await promise.all([endEstadoUpdatePage.setUfInput('uf'), endEstadoUpdatePage.setDescricaoInput('descricao')]);

    expect(await endEstadoUpdatePage.getUfInput()).to.eq('uf', 'Expected Uf value to be equals to uf');
    expect(await endEstadoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');

    await endEstadoUpdatePage.save();
    expect(await endEstadoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await endEstadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EndEstado', async () => {
    const nbButtonsBeforeDelete = await endEstadoComponentsPage.countDeleteButtons();
    await endEstadoComponentsPage.clickOnLastDeleteButton();

    endEstadoDeleteDialog = new EndEstadoDeleteDialog();
    expect(await endEstadoDeleteDialog.getDialogTitle()).to.eq('crudclienteApp.endEstado.delete.question');
    await endEstadoDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(endEstadoComponentsPage.title), 5000);

    expect(await endEstadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
