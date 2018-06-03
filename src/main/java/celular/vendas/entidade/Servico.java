package celular.vendas.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;





@Entity
@Table(name = "servico")
public class Servico implements Serializable {

	private static final long serialVersionUID = 5152724164913423114L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idservico")
	private Integer idservico;
	
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "valor")
	private float valor;
	
	@Column(name = "dtabertura")
	private Date dtabertura;
	
	@Column(name = "dtentrega")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cel", referencedColumnName = "id_cel", nullable = false)
	private Celular celular;

	public Celular getCelular() {
		return celular;
	}

	public void setCelular(Celular celular) {
		this.celular = celular;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idservico == null) ? 0 : idservico.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servico other = (Servico) obj;
		if (idservico == null) {
			if (other.idservico != null)
				return false;
		} else if (!idservico.equals(other.idservico))
			return false;
		return true;
	}

	
}
