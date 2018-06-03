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

	private Integer idPessoa;
	private String dsNome;
	private Integer vlIdade;
	private List<ServicoBean> listaTelefone;

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public List<ServicoBean> getListaTelefone() {
		return listaTelefone;
	}

	public void setListaTelefone(List<ServicoBean> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}

	public void setVlIdade(Integer vlIdade) {
		this.vlIdade = vlIdade;
	}

	public Integer getVlIdade() {
		return vlIdade;
	}

}
