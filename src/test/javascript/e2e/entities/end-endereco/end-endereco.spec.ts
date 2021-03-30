import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EndEnderecoComponentsPage, EndEnderecoDeleteDialog, EndEnderecoUpdatePage } from './end-endereco.page-object';

const expect = chai.expect;

describe('EndEndereco e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let endEnderecoComponentsPage: EndEnderecoComponentsPage;
  let endEnderecoUpdatePage: EndEnderecoUpdatePage;
  let endEnderecoDeleteDialog: EndEnderecoDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EndEnderecos', async () => {
    await navBarPage.goToEntity('end-endereco');
    endEnderecoComponentsPage = new EndEnderecoComponentsPage();
    await browser.wait(ec.visibilityOf(endEnderecoComponentsPage.title), 5000);
    expect(await endEnderecoComponentsPage.getTitle()).to.eq('crudclienteApp.endEndereco.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(endEnderecoComponentsPage.entities), ec.visibilityOf(endEnderecoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EndEndereco page', async () => {
    await endEnderecoComponentsPage.clickOnCreateButton();
    endEnderecoUpdatePage = new EndEnderecoUpdatePage();
    expect(await endEnderecoUpdatePage.getPageTitle()).to.eq('crudclienteApp.endEndereco.home.createOrEditLabel');
    await endEnderecoUpdatePage.cancel();
  });

  it('should create and save EndEnderecos', async () => {
    const nbButtonsBeforeCreate = await endEnderecoComponentsPage.countDeleteButtons();

    await endEnderecoComponentsPage.clickOnCreateButton();

    await promise.all([
      endEnderecoUpdatePage.setNomeParaOEnderecoInput('nomeParaOEndereco'),
      endEnderecoUpdatePage.setCepInput('cep'),
      endEnderecoUpdatePage.setCidadeInput('cidade'),
      endEnderecoUpdatePage.setBairroInput('bairro'),
      endEnderecoUpdatePage.setLogradouroInput('logradouro'),
      endEnderecoUpdatePage.setNumeroInput('numero'),
      endEnderecoUpdatePage.setComplementoInput('complemento'),
      endEnderecoUpdatePage.setReferenciaInput('referencia'),
      endEnderecoUpdatePage.estadoSelectLastOption(),
      endEnderecoUpdatePage.clienteSelectLastOption(),
    ]);

    expect(await endEnderecoUpdatePage.getNomeParaOEnderecoInput()).to.eq(
      'nomeParaOEndereco',
      'Expected NomeParaOEndereco value to be equals to nomeParaOEndereco'
    );
    expect(await endEnderecoUpdatePage.getCepInput()).to.eq('cep', 'Expected Cep value to be equals to cep');
    expect(await endEnderecoUpdatePage.getCidadeInput()).to.eq('cidade', 'Expected Cidade value to be equals to cidade');
    expect(await endEnderecoUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
    expect(await endEnderecoUpdatePage.getLogradouroInput()).to.eq('logradouro', 'Expected Logradouro value to be equals to logradouro');
    expect(await endEnderecoUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
    expect(await endEnderecoUpdatePage.getComplementoInput()).to.eq(
      'complemento',
      'Expected Complemento value to be equals to complemento'
    );
    expect(await endEnderecoUpdatePage.getReferenciaInput()).to.eq('referencia', 'Expected Referencia value to be equals to referencia');

    await endEnderecoUpdatePage.save();
    expect(await endEnderecoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await endEnderecoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EndEndereco', async () => {
    const nbButtonsBeforeDelete = await endEnderecoComponentsPage.countDeleteButtons();
    await endEnderecoComponentsPage.clickOnLastDeleteButton();

    endEnderecoDeleteDialog = new EndEnderecoDeleteDialog();
    expect(await endEnderecoDeleteDialog.getDialogTitle()).to.eq('crudclienteApp.endEndereco.delete.question');
    await endEnderecoDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(endEnderecoComponentsPage.title), 5000);

    expect(await endEnderecoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
