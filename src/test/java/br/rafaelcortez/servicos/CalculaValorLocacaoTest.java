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
	}
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 3, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 1, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 1, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 1, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 1, 4.0);
	
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
		Usuario usuario = new Usuario("Rafael");
		
		//acao	
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1=14
		MatcherAssert.assertThat(resultado.getValor(), is(valorLocacao));
	}

}
