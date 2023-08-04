package lojaLN.pedidos.orcamento;

import lojaLN.excepcoes.PedidoNaoExisteException;

import java.io.Serializable;
import java.util.*;

public class PedidosOrcamento implements Serializable {

    private final Map<UUID, PedidoOrcamento> pedidosOrcamento;

    public PedidosOrcamento() {
        this.pedidosOrcamento = new HashMap<>();
    }

    public PedidoOrcamento[] getPedidosOrcamento() {
        return pedidosOrcamento.values().toArray(PedidoOrcamento[]::new);
    }

    /**
	 * Adiciona um pedido de orçamento.
     * @param idEquipamento o id do equipamento.
     * @param descricao descricao do pedido.
     */
    public void adicionaPedidoOrcamento(UUID idEquipamento, String descricao) {
        pedidosOrcamento.put(idEquipamento, new PedidoOrcamento(idEquipamento, descricao));
    }

    /**
	 * Remove um pedido de orçamento.
	 * @param idEquipamento o id do equipamento.
	 */
    public PedidoOrcamento removerPedidoOrcamento(UUID idEquipamento) throws PedidoNaoExisteException {
        PedidoOrcamento pedido = pedidosOrcamento.remove(idEquipamento);
        if (pedido == null) {
            throw new PedidoNaoExisteException(idEquipamento);
        }
        return pedido;
    }
}