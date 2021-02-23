package br.ce.wcaquino.servicos;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import br.ce.wcaquino.entidades.exceptions.DivisaoPorZeroException;

import static org.hamcrest.CoreMatchers.is;

public class CalculadoraTest {
		
	@Test
	public void testarSoma() {
		
		//cenario
		int a = 2;
		int b = 10;
		
		//acao
		Calculadora calc = new Calculadora();
		int soma = calc.somar(a, b);
		
		//verificacao
		assertThat(12, is(soma));

	}
	
	@Test
	public void testarSubtracao() {
		
		//cenario
		int a = 10;
		int b = 7;
		
		//acao
		Calculadora calc = new Calculadora();
		int subtracao = calc.subtrair(a,b);
		
		//verificacao
		assertThat(3, is(subtracao));
	}
	
	@Test
	public void testarMultiplicaca() {
		
		//cenario
		int a = 10;
		int b = 5;
		
		//acao
		Calculadora calc = new Calculadora();
		int multiplicacao = calc.multiplicacao(a,b);
		
		//verificacao
		assertThat(50, is(multiplicacao));
	}
	
	@Test
	public void testarDivisao() throws DivisaoPorZeroException {
		
		//cenario
		int a = 30;
		int b = 5;
		
		//acao
		Calculadora calc = new Calculadora();
		int divisao = calc.divisao(a, b);
		
		//verificacao
		assertThat(6, is(divisao));
	}
	
	@Test(expected = DivisaoPorZeroException.class)
	public void testarDivisaoPorZero() throws DivisaoPorZeroException  {
		//cenario
		int a = 10;
		int b = 0;
		
		//acao
		Calculadora calc = new Calculadora();
		calc.divisao(a, b);
		
	}
}
