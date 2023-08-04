package lojaLN.pedidos;

import lojaDAO.PedidosDAO;
import lojaLN.excepcoes.*;
import lojaLN.pedidos.clientes.Clientes;
import lojaLN.pedidos.equipamentos.Equipamento;
import lojaLN.pedidos.equipamentos.EquipamentoAEntregar;
import lojaLN.pedidos.equipamentos.Equipamentos;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.expresso.PedidosExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.orcamento.PedidosOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;
import lojaLN.pedidos.reparacao.PedidosReparacao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GestPedidos implements IGestPedidos, Serializable {

    private final PedidosOrcamento pedidosOrcamento;
    private final PedidosReparacao pedidosReparacao;
    private final PedidosExpresso pedidosExpresso;
    private final Equipamentos equipamentos;
    private final Clientes clientes;

    public GestPedidos() {
        this.pedidosOrcamento = new PedidosOrcamento();
        this.pedidosReparacao = new PedidosReparacao();
        this.pedidosExpresso = new PedidosExpresso();
        this.equipamentos = new Equipamentos();
        this.clientes = new Clientes();
    }

    public static GestPedidos getInstance() {
        return (GestPedidos) PedidosDAO.getInstance();
    }

    public PedidoOrcamento[] getPedidosOrcamentoPendentes() {
        return pedidosOrcamento.getPedidosOrcamento();
    }

    @Override
    public void redirecionarEmail(String email, String idFuncionario) throws FuncionarioNaoExisteException {
        // TODO: ?
    }

    @Override
    public void arquivarOrcamento(UUID idEquipamento) throws EquipamentoNaoExisteException {
        // TODO: ?
    }

    @Override
    public void enviarPedidoRecolhaEnquipamento(UUID idEquipamento) throws EquipamentoNaoExisteException {
        // TODO: Enviar o email aqui
    }

    @Override
    public void notificarUltrapasseDePreco(String idCliente, UUID idEquipamento) {
        // TODO: ?
    }

    @Override
    public UUID equipamentoEmPedidoOrcamento(PedidoOrcamento orcamento) {
        return null;
    }

    @Override
    public void enviarOrcamentoCliente(String idCliente) {
        // TODO: ?
    }

    @Override
    public Set<EquipamentoAEntregar> getEquipamentosAEntregar(String idCliente) {
        return equipamentos.getEquipamentosAEntregar(idCliente);
    }

    @Override
    public boolean verificaDisponibilidadeServicoExpresso() {
        return pedidosExpresso.verificaDisponibilidadeServicoExpresso();
    }

    @Override
    public PedidoExpresso proximoPedidoExpresso() {
        return pedidosExpresso.getPedido();
    }

    @Override
    public void registarCliente(String idCliente, String nome, String email, String contacto) throws ClienteJaRegistadoException {
        clientes.registarCliente(idCliente, nome, email, contacto);
    }

    public void registarPedidoOrcamento(String idCliente, String descricao) {
        UUID idEquipamento = equipamentos.registarEquipamento(idCliente);
        pedidosOrcamento.adicionaPedidoOrcamento(idEquipamento, descricao);
    }

    public PedidoExpresso concluirReparacaoExpresso() {
        return pedidosExpresso.concluirReparacao();
    }

    /**
     * É preciso alterar os atendimentos do funcionário
     *
     * @param idCliente
     * @param descricao
     */
    @Override
    public void registarPedidoExpresso(String idCliente, String descricao) {
        Equipamento equipamento = new Equipamento(idCliente);
        pedidosExpresso.adicionaPedidoExpresso(equipamento.getId(), descricao);
    }

    @Override
    public PedidoOrcamento removerPedidoOrcamento(UUID idEquipamento) throws PedidoNaoExisteException {
        return pedidosOrcamento.removerPedidoOrcamento(idEquipamento);
    }

    public PedidoReparacao repararEquipamento(Equipamento equipamento)
            throws PedidoNaoExisteException, ClienteNaoExisteException, EquipamentoNaoExisteException {

        PedidoReparacao pedido = pedidosReparacao.removerPedidoValido(equipamento.getId());
        equipamentos.equipamentoProntoEntregar(equipamento, pedido.getCustoAtual());

        return pedido;
    }

    public List<PedidoReparacao> getPedidosReparacao() {
        return pedidosReparacao.getPedidosReparacao();
    }

    @Override
    public PedidoReparacao getPedidoReparacao(UUID idEquipamento) throws PedidoNaoExisteException {
        return pedidosReparacao.getPedido(idEquipamento);
    }

    @Override
    public void levantarEquipamento(EquipamentoAEntregar equipamento)
            throws ClienteNaoExisteException, EquipamentoNaoExisteException {
        equipamentos.levantarEquipamento(equipamento);
    }

    @Override
    public void equipamentoAbandonado(Equipamento e) throws ClienteNaoExisteException, EquipamentoNaoExisteException {
        equipamentos.abandonarEquipamento(e);
    }

}