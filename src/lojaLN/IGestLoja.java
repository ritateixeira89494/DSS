package lojaLN;

import lojaLN.excepcoes.*;
import lojaLN.operarios.Operario;
import lojaLN.operarios.excepcoes.IdJaExisteException;
import lojaLN.operarios.excepcoes.NaoSePodeCriarNovosAdminException;
import lojaLN.operarios.excepcoes.TecnicoNaoExisteException;
import lojaLN.operarios.excepcoes.TipoInvalidoException;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Tecnico;
import lojaLN.pedidos.equipamentos.Equipamento;
import lojaLN.pedidos.equipamentos.EquipamentoAEntregar;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Interface de uma instância de gestão de uma loja.
 */
public interface IGestLoja {

    /**
     * Guarda uma instância dos objetos da loja.
     */
    void guardar();


    // ---------------------------- Use case 2 → Registar Cliente

    /**
     * Regista um cliente.
     *
     * @param idCliente o NIF do cliente.
     * @param nome      o nome do cliente.
     * @param email     o email do cliente.
     * @param contacto  o contacto do cliente.
     * @throws ClienteJaRegistadoException caso o cliente já esteja registado.
     */
    void registarCliente(String idCliente, String nome, String email, String contacto) throws ClienteJaRegistadoException;

    // ---------------------------- Use case 4 → Registar pedido de orçamento

    /**
     * Regista um pedido de orçamento.
     *
     * @param idFuncionario o id do funcionário.
     * @param idEquipamento o id do equipamento.
     * @param descricao     a descrição do pedido de orçamento.
     * @throws EquipamentoNaoExisteException caso o equipamento não exista.
     */
    void registarPedidoOrcamento(String idFuncionario, String idEquipamento, String descricao)
            throws EquipamentoNaoExisteException, FuncionarioNaoExisteException;


    // ---------------------------- Use case 5 → Realizar orçamento de loja.pedidos.equipamentos.Equipamento

    /**
     * Retorna os pedidos de orçamento pendentes.
     *
     * @return os pedidos de orçamento pendentes.
     */
    PedidoOrcamento[] getPedidosOrcamentoPendentes();


    // ---------------------------- Use case 6 → Registar pedido de Reparação

    PedidoReparacao getPedidoReparacao(UUID idEquipamento) throws PedidoNaoExisteException;

    void redirecionarEmail(String email, String idFuncionario) throws FuncionarioNaoExisteException;

    void notificarUltrapasseDePreco(String idCliente, UUID idEquipamento);

    void arquivarOrcamento(UUID idEquipamento) throws EquipamentoNaoExisteException;

    void enviarPedidoRecolhaEquipamento(UUID idEquipamento) throws EquipamentoNaoExisteException;

    UUID equipamentoEmPedidoOrcamento(PedidoOrcamento orcamento);

    void concluirReparacao(String idTecnico, Equipamento equipamento)
            throws PedidoNaoExisteException, ClienteNaoExisteException, EquipamentoNaoExisteException, TecnicoNaoExisteException;

    void enviarOrcamentoCliente(String idCliente);

    // ---------------------------- Use case 7 → Registar pedido de Reparação

    /**
     * Retorna os pedidos de reparação aceites.
     *
     * @return os pedidos de reparação aceites.
     */
    List<PedidoReparacao> getPedidosReparacao();

    // ---------------------------- Use case 8 → Registar levantamento equipamento

    void registarLevantamentoEquipamento(String idFuncionario, EquipamentoAEntregar equipamento)
            throws ClienteNaoExisteException, EquipamentoNaoExisteException, FuncionarioNaoExisteException;

    void equipamentoAbandonado(Equipamento e) throws ClienteNaoExisteException, EquipamentoNaoExisteException;

    /**
     * Retorna os equipamentos de um dado cliente prontos a entregar.
     *
     * @param idCliente o id do cliente.
     * @return os equipamentos a entregar.
     */
    Set<EquipamentoAEntregar> getEquipamentosAEntregar(String idCliente);

    // ---------------------------- Use case 9 → Registar Pedido de Serviço Expresso

    void registarPedidoExpresso(String idCliente, String descricao);

    /**
     * Verifica a disponibilidade do serviço de reparação expresso.
     *
     * @return true se estiver disponível.
     */
    boolean verificaDisponibilidadeServicoExpresso();

    // ----------------------------- Use case 10 → Reparar Equipamento's de Serviço Expresso

    /**
     * Retorna o proximo pedido da lista de pedidos expressos.
     * Não remove o pedido da lista de pedidos expressos.
     *
     * @return retorna o proximo pedido da lista de pedidos expressos.
     */
    PedidoExpresso proximoPedidoExpresso();

    /**
     * Conclui a reparação do próximo pedido expresso.
     * Para obter o proximo pedido expresso utiliza-se o método __proximoPedidoExpresso__.
     *
     * @param idTecnico o id do técnico.
     */
    void concluirReparacaoExpresso(String idTecnico) throws TecnicoNaoExisteException;

    /**
     * Notifica o cliente que o seu equipamento foi reparado (Reparação expresso).
     *
     * @param idEquipamento id do equipamento.
     */
    void notificarClienteSMS(UUID idEquipamento);

    /**
     * Conclui um pedido de orçamento.
     *
     * @param idTecnico     o id do tecnico.
     * @param idEquipamento o id do equipamento.
     * @throws PedidoNaoExisteException  caso o pedido não exista.
     * @throws TecnicoNaoExisteException caso o técnico não exista.
     */
    void concluirPedidoOrcamento(String idTecnico, UUID idEquipamento) throws PedidoNaoExisteException, TecnicoNaoExisteException;

    // ------------------------------------------ Operações Operarios

    // Use case 1 → Autentificar Operário

    /**
     * Efetua o login de um operário.
     *
     * @param id       o id do operário.
     * @param password a password do operário.
     * @return o operário.
     * @throws OperarioNaoExisteException caso o operário não exista.
     * @throws PasswordInvalidaException  caso a password seja inválida.
     */
    Operario login(String id, String password) throws OperarioNaoExisteException, PasswordInvalidaException;

    // ---------------------------- Operações Admin

    /**
     * Adiciona um operário e retorna o seu id para login.
     *
     * @param id           o id do operário.
     * @param password     a password do operário.
     * @param tipoOperario o tipo do operário.
     */
    void adicionarOperario(String id, String password, String tipoOperario) throws IdJaExisteException, NaoSePodeCriarNovosAdminException, TipoInvalidoException;

    /**
     * Remove um operario.
     *
     * @param idOperario o id do operario.
     */
    void removerOperario(String idOperario) throws OperarioNaoExisteException;


    // ---------------------------- Operações Gestor

    /**
     * Retorna a lista de técnicos e consequentemente as informações armazenadas dentro da sua classe.
     *
     * @return lista de técnicos.
     */
    List<Tecnico> getTecnicos();

    /**
     * Retorna a lista de funcionários e consequentemente as informações armazenadas dentro da sua classe.
     *
     * @return lista de funcionários.
     */
    List<Funcionario> getFuncionarios();
}