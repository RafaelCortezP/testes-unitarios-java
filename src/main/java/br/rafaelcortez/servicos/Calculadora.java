package br.rafaelcortez.servicos;

import br.rafaelcortez.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		System.out.println("Execu��o m�todo somar");
		return a +b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int a, int b) throws NaoPodeDividirPorZeroException {
		if(b == 0)
			throw new NaoPodeDividirPorZeroException();
		
		return a/b;
	}
	
	public int dividir2(String a, String b) {
		return Integer.valueOf(a) / Integer.valueOf(b);
	}
	
	public void imprime() {
		System.out.println("Passei aqui");
	}

}
