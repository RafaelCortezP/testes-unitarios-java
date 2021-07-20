package br.rafaelcortez.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.exceptions.FilmeSemEstoqueException;
import br.rafaelcortez.exceptions.LocadoraException;
import br.rafaelcortez.utils.DataUtils;

public class LocacaoServiceTest {
	
	LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		//System.out.println("before");
	}
	
	/*@After
	public void tearDown() {
		System.out.println("after");
	}
	
	@BeforeClass
	public static void setupClass() {
		
		System.out.println("before class");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("after class");
	}*/
	
	@Test
	public void testeLocacao() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 2, 5.0);
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		 error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
			 
	}
	
	//Forma Elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 0, 5.0);
			
		//acao
		service.alugarFilme(usuario, filme);	
		
		System.out.println("Forma Elegante");
	}
	
	//Outras formas de realizar o teste FilmeSemEstoque
	
	//Forma Robusta
	/*@Test
	public void testeLocacao_filmeSemEstoque2() {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 0, 5.0);
			
		//acao
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("deveria ter lançado uma exceção");
		} catch (Exception e) {
			MatcherAssert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}	 
	}*/
	
	//Forma Nova
	/*@Test
	public void testeLocacao_filmeSemEstoque3() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
			
		//acao
		service.alugarFilme(usuario, filme);
	}*/
	
	//Forma Robusta
	@Test
	public void testeLocacao_UsuarioVazio() throws FilmeSemEstoqueException{
		
		//cenario
		Filme filme = new Filme("Filme 1", 1, 5.0);
			
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail("deveria ter lançado uma exceção");
		} catch (LocadoraException e) {
			MatcherAssert.assertThat(e.getMessage(), is("Usuario vazio"));
		}	 
		
	}
	
	//Forma Nova
	@Test
	public void testeLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		//Filme filme = new Filme("Filme 1", 1, 5.0);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
			
		//acao	
		service.alugarFilme(usuario, null);
			
	}
	
	
}
