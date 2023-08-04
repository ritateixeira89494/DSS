package lojaLN.pedidos.reparacao;

import lojaLN.excepcoes.PedidoNaoExisteException;
import lojaLN.pedidos.orcamento.PedidoOrcamento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PedidosReparacao implements Serializable {

    /**
     * Key: id do Equipamento.
     * Value: Pedido de reparação.
     */
    private final Map<UUID, PedidoReparacao> pedidosReparacao;

    /**
     * Constructor
     */
    public PedidosReparacao() {
        this.pedidosReparacao = new HashMap<>();
    }

    /**
     * Retorna todos os pedidos de reparação aceites.
     *
     * @return todos os pedidos de reparação aceites.
     */
    public List<PedidoReparacao> getPedidosReparacao() {
        return pedidosReparacao.values().stream().filter(ped -> ped.getEstado() == Estado.Aceite).toList();
    }

    /**
     * Retorna o pedido relativo a um dado equipamento.
     *
     * @param idEquipamento o id do equipamento.
     * @return o pedido de reparação relativo ao equipamento.
     * @throws PedidoNaoExisteException caso não exista o pedido.
     */
    public PedidoReparacao getPedido(UUID idEquipamento) throws PedidoNaoExisteException {
        PedidoReparacao pedido = pedidosReparacao.get(idEquipamento);
        if (pedido == null) {
            throw new PedidoNaoExisteException(idEquipamento);
        }
        return pedido;
    }

    /**
     * Remove e retorna o pedido relativo a um dado equipamento.
     *
     * @param idEquipamento o id do equipamento.
     * @return o pedido de reparação relativo ao equipamento.
     * @throws PedidoNaoExisteException caso não exista o pedido.
     */
    public PedidoReparacao removerPedidoValido(UUID idEquipamento) throws PedidoNaoExisteException {
        PedidoReparacao pedido = pedidosReparacao.remove(idEquipamento);
        if (pedido == null) {
            throw new PedidoNaoExisteException(idEquipamento);
        }
        return pedido;
    }
}