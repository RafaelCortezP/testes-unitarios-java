package br.rafaelcortez.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Assume;
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
	
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
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
	public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException{
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Rafael");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(resultado.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
	
}
