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
import org.mockito.Mockito;

import br.rafaelcortez.builders.FilmeBuilder;
import br.rafaelcortez.builders.LocacaoBuilder;
import br.rafaelcortez.builders.UsuarioBuilder;
import br.rafaelcortez.daos.LocacaoDAO;
import br.rafaelcortez.daos.SPCService;
import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.exceptions.FilmeSemEstoqueException;
import br.rafaelcortez.exceptions.LocadoraException;
import br.rafaelcortez.matchers.MatchersProprios;
import br.rafaelcortez.utils.DataUtils;

public class LocacaoServiceTest {
	
	LocacaoService service;
	
	SPCService spc;
	
	LocacaoDAO dao;
	
	EmailService email;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
		
		email = Mockito.mock(EmailService.class);
		service.setEmailService(email);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		 error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		// error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHojeComDiferencaDias(0));
		// error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));
			 
	}
	
	//Forma Elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().agora());
			
		//acao
		service.alugarFilme(usuario, filmes);	
		
		System.out.println("Forma Elegante");
	}
	
	//Forma Robusta
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException{
		
		//cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
			
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("deveria ter lan�ado uma exce��o");
		} catch (LocadoraException e) {
			MatcherAssert.assertThat(e.getMessage(), is("Usuario vazio"));
		}	 
		
	}
	
	//Forma Nova
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
		
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
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
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao	
		//MatcherAssert.assertThat(resultado.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		MatcherAssert.assertThat(resultado.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		//MatcherAssert.assertThat(resultado.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);
		
		//acao	
		try {
			service.alugarFilme(usuario, filmes);
		//verifica��o
			Assert.fail();
		} catch (LocadoraException e) {
			MatcherAssert.assertThat(e.getMessage(), is("Usuario Negativado"));
		}
		
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao().comUsuario(usuario).comDataRetorno(DataUtils.obterDataComDiferencaDias(-2)).agora());
		
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		//acao	
		service.notificarAtrasos();
		
		//verifica��o
		Mockito.verify(email).notificarAtraso(usuario);
	}
	
}
