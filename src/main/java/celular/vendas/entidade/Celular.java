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

		
	}
	
