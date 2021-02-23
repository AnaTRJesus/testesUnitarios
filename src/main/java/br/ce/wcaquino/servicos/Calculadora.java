package br.ce.wcaquino.servicos;


import br.ce.wcaquino.entidades.exceptions.DivisaoPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int multiplicacao(int a, int b) {
		return a * b;
	}

	public int divisao(int a, int b) throws DivisaoPorZeroException {
		if (b  == 0) {
			throw new DivisaoPorZeroException();
		}
		return a/b;
	}
}
