package br.rafaelcortez.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 2, 5.0);
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		 assertThat(locacao.getValor(), is(equalTo(5.0)));
		 assertThat(locacao.getValor(), is(not(6.0)));
		 assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		 assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		
	}

}
