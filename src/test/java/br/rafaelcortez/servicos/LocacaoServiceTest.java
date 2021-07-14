package br.rafaelcortez.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void testeLocacao() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 2, 5.0);
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		 error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		 error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		
	}

}
