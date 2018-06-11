

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
@ManagedBean(name = "celularController")
@RequestScoped
@Controller
public class CelularController {

	@Autowired
	private CelularBean celularBean;
	@Autowired
	private List<CelularBean> listaCelularBean;
	@Autowired
	private CelularService celularService;
	@Autowired
	private ServicoBean servicoBean;

	/**
	 * Construtor da classe de pessoa
	 */
	@SuppressWarnings("unchecked")
	public CelularController() {
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
			celular.setId_cel(celularBean.getId_cel());
			celular.setNome(celularBean.getNome());
			celular.setMarca(celularBean.getMarca());

			// preeche a lista de telefones da tela na lista de telefones persistente
			celular.setListaServico(new ArrayList<Servico>()); 
			for (ServicoBean servicoBean : celularBean.getListaServico()) {
				Servico servico = new Servico();
				servico.setDescricao(servicoBean.getDescricao());
				servico.setIdservico(servicoBean.getIdservico());
				servico.setValor(servicoBean.getValor());
				servico.setDtabertura(servico.getDtabertura());
				servico.setDtentrega(servicoBean.getDtentrega());
				
			/*	telefone.setClTipo(servicoBean.getClTipo());
				telefone.setDsNumero(servicoBean.getDsNumero());
				telefone.setPessoa(pessoa);
				pessoa.getListaTelefone().add(telefone);
			*/	
			}

			getCelularService().incluir(celular);

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

			List<Celular> listaCelular = getCelularService().listar();

			if (listaCelular == null || listaCelular.size() == 0) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
			}

			// preeche a lista de pessoas da tela
			listaCelularBean = new ArrayList<CelularBean>();
			for (Celular celular : listaCelular) {
				CelularBean celularBean = new CelularBean();
				celularBean.setId_cel(celular.getId_cel());
				celularBean.setMarca(celular.getMarca());
				celularBean.setNome(celular.getNome());
				listaCelularBean.add(celularBean);
			/*	celularBean.setIdPessoa(pessoa.getIdPessoa());
				celularBean.setDsNome(pessoa.getDsNome());
				celularBean.setVlIdade((pessoa.getVlIdade() != null && !"".equals(pessoa.getVlIdade()))
						? Integer.parseInt(pessoa.getVlIdade())
						: 0);
				listaCelularBean.add(celularBean);
			*/
			
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

			String id_cel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("id_cel");

			Celular celular = getCelularService().consultar(Integer.parseInt(id_cel));

			if (celular == null || celular.getId_cel() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			// preenche os dados do bean da tela
			celularBean.setId_cel(celular.getId_cel());
			celularBean.setMarca(celular.getMarca());
			celularBean.setNome(celular.getNome());
			
		/*	celularBean.setIdPessoa(pessoa.getIdPessoa());
			celularBean.setDsNome(pessoa.getDsNome());
			celularBean.setVlIdade((pessoa.getVlIdade() != null && !"".equals(pessoa.getVlIdade()))
					? Integer.parseInt(pessoa.getVlIdade())
					: 0);
			*/
			

			// preeche a lista de telefones da tela
			celularBean.setListaServico(new ArrayList<ServicoBean>());
			for (Servico servico: celular.getListaServico()) {
				ServicoBean servicoBean = new ServicoBean();
				servicoBean.setIdservico(servico.getIdservico());
				servicoBean.setDescricao(servico.getDescricao());
				servicoBean.setValor(servico.getValor());
				servicoBean.setDtabertura(servico.getDtabertura());
				servicoBean.setDtentrega(servico.getDtentrega());
				
			/*	servicoBean.setIdTelefone(telefone.getIdTelefone());
				servicoBean.setClTipo(telefone.getClTipo());
				servicoBean.setDsNumero(telefone.getDsNumero());
				celularBean.getListaTelefone().add(servicoBean);
			*/
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
			celularBean.setListaServico(new ArrayList<ServicoBean>());

			this.setSession("servicos", celularBean.getListaServico());

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
/*	public String adicionar() {
		try {

			ServicoBean novo = new ServicoBean();
			novo.set(servicoBean.getClTipo());
			novo.setDsNumero(servicoBean.getDsNumero());

			celularBean.getListaTelefone().add(novo);

			servicoBean = new ServicoBean();

			return "criar";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Criacao nao realizada. Movito: " + e.getMessage());
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}
*/
	/**
	 * Adiciona um telefone de uma pessoa
	 * 
	 * @return
	 */
/*	public String adicionarEditar() {
		try {

			ServicoBean novo = new ServicoBean();
			novo.setClTipo(servicoBean.getClTipo());
			novo.setDsNumero(servicoBean.getDsNumero());

			celularBean.getListaTelefone().add(novo);

			servicoBean = new ServicoBean();

			return "editar";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Criacao nao realizada. Movito: " + e.getMessage());
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}
*/
	/**
	 * Remove um telefone da lista de uma pessoa
	 * 
	 * @return
	 */
	public String remover() {
		try {

			HtmlDataTable telefones = (HtmlDataTable) this.getFacesContext().getViewRoot()
					.findComponent("formulario:telefones");
			celularBean.getListaServico().remove(celularBean.getListaServico().indexOf(telefones.getRowData()));

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

			HtmlInputHidden id_cel = (HtmlInputHidden) this.getFacesContext().getViewRoot()
					.findComponent("formulario:id_cel");

			Celular celular = getCelularService().consultar((Integer) id_cel.getValue());

			if (id_cel == null || celular.getId_cel() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			getCelularService().excluir(celular.getId_cel());

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

			Celular celular = getCelularService().consultar(celularBean.getId_cel());

			if (celular == null || celular.getId_cel() == null) {
				FacesMessage message = new FacesMessage("Nenhum registro encontrado.");
				this.getFacesContext().addMessage("formulario", message);
				return "listar";
			}

			// preenche os dados da tela no objeto persistente
			celular.setNome(celularBean.getNome());
			celular.setMarca(celularBean.getMarca());

			// preeche a lista de telefones da tela na lista de telefones persistente
			celular.setListaServico(new ArrayList<Servico>());
			for (ServicoBean servicoBean : celularBean.getListaServico()) {
				Servico servico = new Servico();
				servico.setIdservico(servicoBean.getIdservico() == 0 ? null : servicoBean.getIdservico());
				servico.setDescricao(servicoBean.getDescricao());
				servico.setDtabertura(servicoBean.getDtabertura());
				servico.setDtentrega(servicoBean.getDtentrega());
				
				servico.setCelular(celular);
				celular.getListaServico().add(servico);
			}

			getCelularService().alterar(celular);
			return "sucesso";

		} catch (Exception e) {
			String msg = "Alteracao nao realizada. Movito: "
					+ ((e instanceof AgendaException ? ((AgendaException) e).getEx().getMessage() : ""));
			FacesMessage message = new FacesMessage(msg);
			this.getFacesContext().addMessage("formulario", message);
			return "falha";
		}
	}

	public CelularService getCelularService() {
		return celularService;
	}

	public void setCelularService(CelularService celularService) {
		this.celularService = celularService;
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

	public ServicoBean getServicoBean() {
		return servicoBean;
	}

	public void setServicoBean(ServicoBean servicoBean) {
		this.servicoBean = servicoBean;
	}

}
