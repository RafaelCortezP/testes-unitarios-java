package br.rafaelcortez.daos;

import java.util.List;

import br.rafaelcortez.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
	
}
