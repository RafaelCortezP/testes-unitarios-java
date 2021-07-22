package br.rafaelcortez.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.rafaelcortez.servicos.CalculaValorLocacaoTest;
import br.rafaelcortez.servicos.CalculadoraTest;
import br.rafaelcortez.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculaValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {

}
