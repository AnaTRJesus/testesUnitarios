package br.ce.wcaquino.servicos;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;


public class LocacaoServiceTest {
	
	LocacaoService service;
	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUp() {
		System.out.println("BeforeClass");
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("AfterClass");
	}
	
	@Before
	public void beforeEachMethod() {
		service = new LocacaoService();
		System.out.println("Before each test");
	}
	
	@After
	public void afterEachMethod() {
		System.out.println("After each test");
	}
	
	@Test
	public void testarLocacao() {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Filme01", 2, 5.0);
		filmes.add(filme);
		
				
		//acao
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filmes);
			Double somaValores = filmes.stream().mapToDouble(x -> x.getPrecoLocacao()).reduce(0, Double::sum);
			errorCollector.checkThat(somaValores, is(5.0));
			errorCollector.checkThat(locacao.getUsuario().getNome(), is("Usuario01"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//verificacao

			Assert.fail("Não deveria lancar excecao");

		}		
	}
	
	@Test
	public void testarLocacaoExcecaoLancada() throws Exception {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Filme01", 2, 5.0);
		filmes.add(filme);	
		//acao
		Locacao locacao;
			
		locacao = service.alugarFilme(usuario, filmes);
		errorCollector.checkThat(locacao.getValor(), is(5.0));
		errorCollector.checkThat(locacao.getUsuario().getNome(), is("Usuario01"));
	
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testarLocacaoExcecaoLancada01() throws Exception {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Filme01", 0, 5.0);
		filmes.add(filme);		
		//acao
		service.alugarFilme(usuario, filmes);
	
	}
	
	@Test
	public void testarLocacaoExcecaoLancada02()  {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Filme01", 0, 5.0);
		filmes.add(filme);		
		//acao
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			assertThat(e.getMessage(),is("Filme sem estoque"));
		}
	}
	
	@Test
	public void testarLocacaoExcecaoLancada03() throws Exception {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme01 = new Filme("Filme01", 0, 5.0);
		filmes.add(filme01);	
		Filme filme02 = new Filme("Filme01", 0, 5.0);
		filmes.add(filme02);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testarLocacaoSemUsuario() throws FilmeSemEstoqueException {
		
		//cenario
		
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Filme01", 2, 5.0);
		filmes.add(filme);		
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("Usuario deveria estar vazio e lancar excecao");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void testarLocacaoSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//acao
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void validarDescontoParaTresFilmes() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme01 = new Filme("Filme01", 3, 4.0);
		filmes.add(filme01);	
		Filme filme02 = new Filme("Filme02", 3, 4.0);
		filmes.add(filme02);
		Filme filme03 = new Filme("Filme03", 3, 4.0);
		filmes.add(filme03);
		
		//acao
		Double valorAluguel = service.alugarFilme(usuario, filmes).getValor();
		
		//verificacao
		assertThat(11.0, is(valorAluguel));
	}
	
	@Test
	public void validarDescontoParaQuatroFilmes() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = Arrays.asList(new Filme("Filme01", 3, 4.0),new Filme("Filme02", 3, 4.0), new Filme("Filme03", 3, 4.0), new Filme("Filme04", 3, 4.0));
		
		//acao
		Double valorAluguel = service.alugarFilme(usuario, filmes).getValor();
		
		//verificacao
		assertThat(13.0, is(valorAluguel));
	}
	
	@Test
	public void validarDescontoParaCincoFilmes() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = Arrays.asList(new Filme("Filme01", 3, 4.0),new Filme("Filme02", 3, 4.0), new Filme("Filme03", 3, 4.0), new Filme("Filme04", 3, 4.0), new Filme("Filme05", 3, 4.0));
		
		//acao
		Double valorAluguel = service.alugarFilme(usuario, filmes).getValor();
		
		//verificacao
		assertThat(14.0, is(valorAluguel));
	}
	
	@Test
	public void validarDescontoParaSeisFilmes() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		
		Usuario usuario = new Usuario("Usuario01");
		List<Filme> filmes = Arrays.asList(new Filme("Filme01", 3, 4.0),new Filme("Filme02", 3, 4.0), new Filme("Filme03", 3, 4.0), new Filme("Filme04", 3, 4.0), new Filme("Filme05", 3, 4.0), new Filme("Filme06", 3, 4.0));
		
		//acao
		Double valorAluguel = service.alugarFilme(usuario, filmes).getValor();
		
		//verificacao
		assertThat(14.0, is(valorAluguel));
	}
}
