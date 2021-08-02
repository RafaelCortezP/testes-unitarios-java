package br.rafaelcortez.servicos;

import br.rafaelcortez.entidades.Usuario;

public interface EmailService {

	public void notificarAtraso(Usuario usuario);
}
