

package celular.vendas.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import celular.vendas.entidade.Celular;
import celular.vendas.entidade.Servico;
import celular.vendas.negocio.CelularService;
import celular.vendas.util.AgendaException;

/**
 * Classe que controla as requisicoes do cliente web
 * 
 * @author Gilcimar
 *
 */
@ManagedBean(name = "pessoaController")
@RequestScoped
@Controller
public class CelularController {

	@Autowired
	private CelularBean celularBean;
	@Autowired
	private List<CelularBean> listaCelularBean;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private TelefoneBean telefoneBean;

	/**
	 * Construtor da classe de pessoa
	 */
	@SuppressWarnings("unchecked")
	public PessoaController() {
		celularBean = new CelularBean();
	}

	/**
	 * Inclui uma pessoa na base de dados
	 * 
	 * @return
	 */
	public String incluir() {
		try {

			Celular celular = new Celular();

			// preenche os dados da tela no objeto persistente
			celular.setId_cel(celularBean.getIdPessoa());
			celular.setNome(celularBean.getDsNome());
			celular.setMarca(celularBean.getVlIdade().toString());

			// preeche a lista de telefones da tela na lista de telefones persistente
			pessoa.setListaTelefone(new ArrayList<Telefone>());
			for (TelefoneBean telefoneBean : celularBean.getListaTelefone()) {
				Telefone telefone = new Telefone();
				telefone.setClTipo(telefoneBean.getClTipo());
				telefone.setDsNumero(telefoneBean.getDsNumero());
				telefone.setPessoa(pessoa);
				pessoa.getListaTelefone().add(telefone);
			}

			getPessoaService().incluir(pessoa);

			return "sucesso";
		} catch (Exception e) {
			String msg = "Inclusao nao realizada. Movito: " + e.getMessage();
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Lista as pessoas cadastradas
	 * 
	 * @return
	 */
	public String listar() {
		try {

			List<Pessoa> listaPessoa = getPessoaService().listar();

			if (listaPessoa == null || listaPessoa.size() == 0) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
			}

			// preeche a lista de pessoas da tela
			listaCelularBean = new ArrayList<CelularBean>();
			for (Pessoa pessoa : listaPessoa) {
				CelularBean celularBean = new CelularBean();
				celularBean.setIdPessoa(pessoa.getIdPessoa());
				celularBean.setDsNome(pessoa.getDsNome());
				celularBean.setVlIdade((pessoa.getVlIdade() != null && !"".equals(pessoa.getVlIdade()))
						? Integer.parseInt(pessoa.getVlIdade())
						: 0);
				listaCelularBean.add(celularBean);
			}

			return "listar";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Listagem nao realizada. Movito: "
					+ ((e instanceof AgendaException ? ((AgendaException) e).getEx().getMessage() : ""));
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Consulta uma pessoa cadastrada
	 * 
	 * @return
	 */
	public String consultar() {
		try {

			String idPessoa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("idPessoa");

			Pessoa pessoa = getPessoaService().consultar(Integer.parseInt(idPessoa));

			if (pessoa == null || pessoa.getIdPessoa() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			// preenche os dados do bean da tela
			celularBean.setIdPessoa(pessoa.getIdPessoa());
			celularBean.setDsNome(pessoa.getDsNome());
			celularBean.setVlIdade((pessoa.getVlIdade() != null && !"".equals(pessoa.getVlIdade()))
					? Integer.parseInt(pessoa.getVlIdade())
					: 0);

			// preeche a lista de telefones da tela
			CelularBean.setListaTelefone(new ArrayList<TelefoneBean>());
			for (Telefone telefone : pessoa.getListaTelefone()) {
				TelefoneBean telefoneBean = new TelefoneBean();
				telefoneBean.setIdTelefone(telefone.getIdTelefone());
				telefoneBean.setClTipo(telefone.getClTipo());
				telefoneBean.setDsNumero(telefone.getDsNumero());
				celularBean.getListaTelefone().add(telefoneBean);
			}

			return "editar";
		} catch (Exception e) {
			String msg = "Consulta nao realizada. Movito: " + e.getMessage();
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Cria uma nova pessoa
	 * 
	 * @return
	 */
	public String criar() {
		try {

			celularBean = new CelularBean();
			celularBean.setListaTelefone(new ArrayList<TelefoneBean>());

			this.setSession("telefones", celularBean.getListaTelefone());

			return "criar";
		} catch (Exception e) {
			String msg = "Criacao nao realizada. Movito: " + e.getMessage();
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Adiciona um telefone de uma pessoa
	 * 
	 * @return
	 */
	public String adicionar() {
		try {

			TelefoneBean novo = new TelefoneBean();
			novo.setClTipo(telefoneBean.getClTipo());
			novo.setDsNumero(telefoneBean.getDsNumero());

			celularBean.getListaTelefone().add(novo);

			telefoneBean = new TelefoneBean();

			return "criar";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Criacao nao realizada. Movito: " + e.getMessage());
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Adiciona um telefone de uma pessoa
	 * 
	 * @return
	 */
	public String adicionarEditar() {
		try {

			TelefoneBean novo = new TelefoneBean();
			novo.setClTipo(telefoneBean.getClTipo());
			novo.setDsNumero(telefoneBean.getDsNumero());

			celularBean.getListaTelefone().add(novo);

			telefoneBean = new TelefoneBean();

			return "editar";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Criacao nao realizada. Movito: " + e.getMessage());
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Remove um telefone da lista de uma pessoa
	 * 
	 * @return
	 */
	public String remover() {
		try {

			HtmlDataTable telefones = (HtmlDataTable) this.getFacesContext().getViewRoot()
					.findComponent("formulario:telefones");
			celularBean.getListaTelefone().remove(celularBean.getListaTelefone().indexOf(telefones.getRowData()));

			return null;
		} catch (Exception e) {
			String msg = "Exclusao nao realizada. Movito: " + e.getMessage();
			;
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Exclui uma pessoa cadastrada
	 * 
	 * @return
	 */
	public String excluir() {
		try {

			HtmlInputHidden idPessoa = (HtmlInputHidden) this.getFacesContext().getViewRoot()
					.findComponent("formulario:idPessoa");

			Pessoa pessoa = getPessoaService().consultar((Integer) idPessoa.getValue());

			if (pessoa == null || pessoa.getIdPessoa() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			getPessoaService().excluir(pessoa.getIdPessoa());

			return "sucesso";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Exclusao nao realizada. Movito: "
					+ ((e instanceof AgendaException ? ((AgendaException) e).getEx().getMessage() : ""));
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	/**
	 * Exclui uma pessoa cadastrada
	 * 
	 * @return
	 */
	public String alterar() {
		try {

			Pessoa pessoa = getPessoaService().consultar(celularBean.getIdPessoa());

			if (pessoa == null || pessoa.getIdPessoa() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			// preenche os dados da tela no objeto persistente
			pessoa.setDsNome(celularBean.getDsNome());
			pessoa.setVlIdade(celularBean.getVlIdade().toString());

			// preeche a lista de telefones da tela na lista de telefones persistente
			pessoa.setListaTelefone(new ArrayList<Telefone>());
			for (TelefoneBean telefoneBean : celularBean.getListaTelefone()) {
				Telefone telefone = new Telefone();
				telefone.setIdTelefone(telefoneBean.getIdTelefone() == 0 ? null : telefoneBean.getIdTelefone());
				telefone.setClTipo(telefoneBean.getClTipo());
				telefone.setDsNumero(telefoneBean.getDsNumero());
				telefone.setPessoa(pessoa);
				pessoa.getListaTelefone().add(telefone);
			}

			getPessoaService().alterar(pessoa);
			return "sucesso";

		} catch (Exception e) {
			String msg = "Alteracao nao realizada. Movito: "
					+ ((e instanceof AgendaException ? ((AgendaException) e).getEx().getMessage() : ""));
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	public PessoaService getPessoaService() {
		return pessoaService;
	}

	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	private FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private Object getSession(String variavel) {
		return this.getFacesContext().getExternalContext().getSessionMap().get(variavel);
	}

	private void setSession(String variavel, Object objeto) {
		this.getFacesContext().getExternalContext().getSessionMap().put(variavel, objeto);
	}

	public CelularBean getCelularBean() {
		return celularBean;
	}

	public void setCelularBean(CelularBean celularBean) {
		this.celularBean = celularBean;
	}

	public List<CelularBean> getListaCelularBean() {
		return listaCelularBean;
	}

	public void setListaCelularBean(List<CelularBean> listaCelularBean) {
		this.listaCelularBean = listaCelularBean;
	}

	public TelefoneBean getTelefoneBean() {
		return telefoneBean;
	}

	public void setTelefoneBean(TelefoneBean telefoneBean) {
		this.telefoneBean = telefoneBean;
	}

}
