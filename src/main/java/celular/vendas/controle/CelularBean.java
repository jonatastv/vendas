package celular.vendas.controle;

import java.util.List;

import org.springframework.stereotype.Component;



/**
 * Classe que representa o formulario web de Pessoa
 * 
 * @author Gilcimar
 *
 */
@Component
public class CelularBean {

	private Integer id_cel;
	private String nome;
	private String marca;
	private List<ServicoBean> listaServico;
	
	
	public Integer getId_cel() {
		return id_cel;
	}
	public void setId_cel(Integer id_cel) {
		this.id_cel = id_cel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public List<ServicoBean> getListaServico() {
		return listaServico;
	}
	public void setListaServico(List<ServicoBean> listaServico) {
		this.listaServico = listaServico;
	}
	
	
	
	
	

}
