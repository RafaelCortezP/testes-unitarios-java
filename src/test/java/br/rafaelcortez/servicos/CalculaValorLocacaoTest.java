package br.rafaelcortez.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.rafaelcortez.builders.FilmeBuilder;
import br.rafaelcortez.builders.UsuarioBuilder;
import br.rafaelcortez.daos.LocacaoDAO;
import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.exceptions.FilmeSemEstoqueException;
import br.rafaelcortez.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculaValorLocacaoTest {
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	LocacaoService service;
	
	@Before
	public void setup() {
		service = new LocacaoService();
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
	}
	
	private static Filme filme1 = FilmeBuilder.umFilme().agora();
	private static Filme filme2 = FilmeBuilder.umFilme().agora();
	private static Filme filme3 = FilmeBuilder.umFilme().agora();
	private static Filme filme4 = FilmeBuilder.umFilme().agora();
	private static Filme filme5 = FilmeBuilder.umFilme().agora();
	private static Filme filme6 = FilmeBuilder.umFilme().agora();
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes 100%"}
			});
	}
	
	@Test
	public void deveCalcularValorLocacoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1=14
		MatcherAssert.assertThat(resultado.getValor(), is(valorLocacao));
	}

}
