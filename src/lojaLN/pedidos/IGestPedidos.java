package lojaLN.pedidos;

import lojaLN.excepcoes.*;
import lojaLN.pedidos.equipamentos.Equipamento;
import lojaLN.pedidos.equipamentos.EquipamentoAEntregar;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IGestPedidos {

    void registarCliente(String idCliente, String nome, String email, String contacto) throws ClienteJaRegistadoException;

    // Use case 4 → Registar pedido de orçamento
    void registarPedidoOrcamento(String idCliente, String descricao) throws EquipamentoNaoExisteException;


    // Use case 5 → Realizar orçamento de loja.pedidos.equipamentos.Equipamento
    PedidoOrcamento[] getPedidosOrcamentoPendentes();

    PedidoOrcamento removerPedidoOrcamento(UUID idEquipamento) throws PedidoNaoExisteException;

    // Use case 6 → Registar pedido de Reparação

    List<PedidoReparacao> getPedidosReparacao();

    void redirecionarEmail(String email, String idFuncionario) throws FuncionarioNaoExisteException;

    void arquivarOrcamento(UUID idEquipamento) throws EquipamentoNaoExisteException;

    void enviarPedidoRecolhaEnquipamento(UUID idEquipamento) throws EquipamentoNaoExisteException;

    UUID equipamentoEmPedidoOrcamento(PedidoOrcamento orcamento);

    void enviarOrcamentoCliente(String idCliente);

    void notificarUltrapasseDePreco(String idCliente, UUID idEquipamento);

    Set<EquipamentoAEntregar> getEquipamentosAEntregar(String idCliente);

    boolean verificaDisponibilidadeServicoExpresso();

    PedidoReparacao repararEquipamento(Equipamento equipamento)
            throws PedidoNaoExisteException, ClienteNaoExisteException, EquipamentoNaoExisteException;

    void registarPedidoExpresso(String idCliente, String descricao);

    void levantarEquipamento(EquipamentoAEntregar equipamento)
            throws ClienteNaoExisteException, EquipamentoNaoExisteException;

    PedidoReparacao getPedidoReparacao(UUID idEquipamento) throws PedidoNaoExisteException;

    // Use case 10 → Reparar loja.pedidos.equipamentos.Equipamento de Serviço Expresso
    PedidoExpresso proximoPedidoExpresso();

    PedidoExpresso concluirReparacaoExpresso();

    void equipamentoAbandonado(Equipamento e) throws ClienteNaoExisteException, EquipamentoNaoExisteException;

}