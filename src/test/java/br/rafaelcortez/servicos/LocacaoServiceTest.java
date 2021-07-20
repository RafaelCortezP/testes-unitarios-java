package br.rafaelcortez.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
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
	public void deveAlugarFilme() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		 error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
			 
	}
	
	//Forma Elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
			
		//acao
		service.alugarFilme(usuario, filmes);	
		
		System.out.println("Forma Elegante");
	}
	
	//Outras formas de realizar o teste FilmeSemEstoque
	
	//Forma Robusta
	/*@Test
	public void naoDeveAlugarFilmeSemEstoque2() {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
			
		//acao
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail("deveria ter lançado uma exceção");
		} catch (Exception e) {
			MatcherAssert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}	 
	}*/
	
	//Forma Nova
	/*@Test
	public void naoDeveAlugarFilmeSemEstoque3() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
			
		//acao
		service.alugarFilme(usuario, filmes);
	}*/
	
	//Forma Robusta
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException{
		
		//cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
			
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("deveria ter lançado uma exceção");
		} catch (LocadoraException e) {
			MatcherAssert.assertThat(e.getMessage(), is("Usuario vazio"));
		}	 
		
	}
	
	//Forma Nova
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		//Filme filme = new Filme("Filme 1", 1, 5.0);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
			
		//acao	
		service.alugarFilme(usuario, null);
			
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0));
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3=11
		MatcherAssert.assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0),
				new Filme("Filme 4", 1, 4.0));
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2=13
		MatcherAssert.assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0),
				new Filme("Filme 4", 1, 4.0),
				new Filme("Filme 5", 1, 4.0));
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1=14
		MatcherAssert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0),
				new Filme("Filme 4", 1, 4.0),
				new Filme("Filme 5", 1, 4.0),
				new Filme("Filme 6", 1, 4.0));
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1+0=14
		MatcherAssert.assertThat(resultado.getValor(), is(14.0));
	}
	
}
