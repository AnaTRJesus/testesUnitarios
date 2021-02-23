package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException  {
				
		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		for(Filme filme: filmes) {
			if(filme.getEstoque() == 0){
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
	
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Long quantidadeFilmes = filmes.stream().count();
		switch (quantidadeFilmes.intValue()) {
			case 3:
				filmes.get(2).setPrecoLocacao(filmes.get(2).getPrecoLocacao() - (filmes.get(2).getPrecoLocacao() * 0.25));
				break;
			case 4:
				filmes.get(2).setPrecoLocacao(filmes.get(2).getPrecoLocacao() - (filmes.get(2).getPrecoLocacao() * 0.25));
				filmes.get(3).setPrecoLocacao(filmes.get(3).getPrecoLocacao() - (filmes.get(3).getPrecoLocacao() * 0.50));
				break;
			case 5:
				filmes.get(2).setPrecoLocacao(filmes.get(2).getPrecoLocacao() - (filmes.get(2).getPrecoLocacao() * 0.25));
				filmes.get(3).setPrecoLocacao(filmes.get(3).getPrecoLocacao() - (filmes.get(3).getPrecoLocacao() * 0.50));
				filmes.get(4).setPrecoLocacao(filmes.get(4).getPrecoLocacao() - (filmes.get(4).getPrecoLocacao() * 0.75));
				break;
			case 6:
				filmes.get(2).setPrecoLocacao(filmes.get(2).getPrecoLocacao() - (filmes.get(2).getPrecoLocacao() * 0.25));
				filmes.get(3).setPrecoLocacao(filmes.get(3).getPrecoLocacao() - (filmes.get(3).getPrecoLocacao() * 0.50));
				filmes.get(4).setPrecoLocacao(filmes.get(4).getPrecoLocacao() - (filmes.get(4).getPrecoLocacao() * 0.75));
				filmes.get(5).setPrecoLocacao(filmes.get(5).getPrecoLocacao() - (filmes.get(5).getPrecoLocacao() * 1.0));
				break;
		}
		Double totalLocacao = filmes.stream().mapToDouble(x -> x.getPrecoLocacao()).reduce(0, (a,b) -> a+b);
		locacao.setValor(totalLocacao);
			
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}