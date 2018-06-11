package celular.vendas.controle;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Classe que representa a parte de telefone no formulario web de Pessoa
 * 
 * @author Gilcimar
 *
 */
@Component
public class ServicoBean {

	private Integer idservico;
	private String descricao;
	private float valor;
	private Date dtabertura;
	private Date dtentrega;
	
	
	
	
	public Integer getIdservico() {
		return idservico;
	}
	public void setIdservico(Integer idservico) {
		this.idservico = idservico;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Date getDtabertura() {
		return dtabertura;
	}
	public void setDtabertura(Date dtabertura) {
		this.dtabertura = dtabertura;
	}
	public Date getDtentrega() {
		return dtentrega;
	}
	public void setDtentrega(Date dtentrega) {
		this.dtentrega = dtentrega;
	}
	
	

}
