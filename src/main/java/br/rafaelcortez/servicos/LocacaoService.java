package br.rafaelcortez.servicos;

import static br.rafaelcortez.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.rafaelcortez.entidades.Filme;
import br.rafaelcortez.entidades.Locacao;
import br.rafaelcortez.entidades.Usuario;
import br.rafaelcortez.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	public static void main(String[] args) {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Filme 1", 2, 5.0);
			
		//acao
		 Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		 System.out.println(locacao.getValor() == 5.0);
		 System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		 System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
	}
}