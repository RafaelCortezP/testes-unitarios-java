package br.rafaelcortez.builders;

import br.rafaelcortez.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {
		
	}

	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Rafael");
		return builder;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
