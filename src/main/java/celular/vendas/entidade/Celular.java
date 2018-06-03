package celular.vendas.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;






	@Entity
	@Table(name = "celular")
	public class Celular implements Serializable {

		private static final long serialVersionUID = 6487849002108370775L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_cel")
		private Integer id_cel;

		@Column(name = "nome")
		private String nome;
		
		@Column(name = "marca")
		private String marca;

		public String getMarca() {
			return marca;
		}

		public void setMarca(String marca) {
			this.marca = marca;
		}

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

		
		@OneToMany(mappedBy = "servico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Servico> listaServico;

		public List<Servico> getListaTelefone() {
			return listaServico;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id_cel == null) ? 0 : id_cel.hashCode());
			return result;
		}
		
		
		public List<Servico> getListaServico() {
			return listaServico;
		}

		public void setListaServico(List<Servico> listaServico) {
			this.listaServico = listaServico;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Celular other = (Celular) obj;
			if (id_cel == null) {
				if (other.id_cel != null)
					return false;
			} else if (!id_cel.equals(other.id_cel))
				return false;
			return true;
		}
		
	}
	
