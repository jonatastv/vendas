
package celular.vendas.negocio;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import celular.vendas.entidade.Celular;
import celular.vendas.entidade.Servico;
import celular.persistencia.CelularDAO;
import celular.persistencia.ServicoDAO;
import celular.vendas.util.AgendaException;

/**
 * Classe que define as operacoes da camada de negocio de Celular
 * 
 * @author Gilcimar
 *
 */
@Service
@Transactional
public class CelularServiceImpl implements CelularService {

	// Interface da persistencia
	private CelularDAO celularDAO;
	// Interface da persistencia
	private ServicoDAO servisoDAO;

	public ServicoDAO getServicoDAO() {
		return servisoDAO;
	}

	@Autowired
	public void setServicoDAO(ServicoDAO servisoDAO) {
		this.servisoDAO = servisoDAO;
	}

	public CelularDAO getCelularDAO() {
		return celularDAO;
	}

	@Autowired
	public void setCelularDAO(CelularDAO celularDAO) {
		this.celularDAO = celularDAO;
	}

	/**
	 * Inclui uma celular
	 * 
	 * @param celular
	 * @return
	 * @throws AgendaException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Celular incluir(Celular celular) throws AgendaException {
		return getCelularDAO().incluir(celular);
	}

	/**
	 * Altera uma celular
	 * 
	 * @param celular
	 * @return
	 * @throws AgendaException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Celular alterar(Celular celular) throws AgendaException {

		// exclui os itens da base que foram removidos da tela
		Celular celularExistente = this.consultar(celular.getId_cel());
		for (Servico servico : celularExistente.getListaServico()) {
			if (!celular.getListaServico().contains(servico)) {
				getServicoDAO().excluir(servico.getIdservico());
			}
		}

		return getCelularDAO().alterar(celular);
	}

	/**
	 * Exclui uma celular
	 * 
	 * @param celular
	 * @throws AgendaException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void excluir(Integer id) throws AgendaException {

		// exclui todos os itens antes de excluir a celular
		Celular celularExistente = this.consultar(id);
		for (Servico servico : celularExistente.getListaServico()) {
			getServicoDAO().excluir(servico.getIdservico());
		}

		getCelularDAO().excluir(id);
	}

	/**
	 * Consulta um celular pelo identificador
	 * 
	 * @param id
	 * @return
	 * @throws AgendaException
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Celular consultar(Integer id) throws AgendaException {
		Celular celular = getCelularDAO().consultar(id);
		// Inicializa a lista de Servicos
		Hibernate.initialize(celular.getListaServico());
		return celular;
	}

	/**
	 * Lista todas as celulars cadastradas
	 * 
	 * @return
	 * @throws AgendaException
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Celular> listar() throws AgendaException {
		return getCelularDAO().listar();
	}

}
