package br.com.rocksti.crudcliente.cucumber;

import br.com.rocksti.crudcliente.CrudclienteApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CrudclienteApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
