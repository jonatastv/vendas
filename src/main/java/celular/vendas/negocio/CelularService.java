
package celular.vendas.negocio;

import java.util.List;

import celular.vendas.entidade.Celular;
import celular.vendas.util.AgendaException;

/**
 * Interface que define as operacoes da camada de negocio de Pessoa
 * 
 * @author Gilcimar
 *
 */
public interface CelularService {

	/**
	 * Inclui uma pessoa
	 * 
	 * @param celular
	 * @return
	 * @throws AgendaException
	 */
	public Celular incluir(Celular celular) throws AgendaException;

	/**
	 * Altera uma Celular
	 * 
	 * @param celular
	 * @return
	 * @throws AgendaException
	 */
	public Celular alterar(Celular celular) throws AgendaException;

	/**
	 * Exclui uma Celular
	 * 
	 * @param id
	 * @throws AgendaException
	 */
	public void excluir(Integer id) throws AgendaException;

	/**
	 * Consulta uma Celular pelo identificador
	 * 
	 * @param id
	 * @return
	 * @throws AgendaException
	 */
	public Celular consultar(Integer id) throws AgendaException;

	/**
	 * Lista todas as Celulares cadastradas
	 * 
	 * @return
	 * @throws AgendaException
	 */
	public List<Celular> listar() throws AgendaException;

}
