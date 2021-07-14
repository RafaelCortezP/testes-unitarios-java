package br.rafaelcortez.servicos;


import java.util.Date;

import org.junit.Assert;
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
		 Assert.assertTrue(locacao.getValor() == 5.0);
		 Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		 Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
	}

}