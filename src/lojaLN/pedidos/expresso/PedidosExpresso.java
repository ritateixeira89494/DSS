package lojaLN.pedidos.expresso;

import lojaLN.pedidos.excepcoes.PedidoJaExisteException;
import lojaLN.pedidos.excepcoes.PedidoNaoExisteException;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

public class PedidosExpresso implements Serializable {
    private static final int MAX_SIZE = 100;
    private final Queue<PedidoExpresso> pedidos;

    public PedidosExpresso() {
        pedidos = new ArrayDeque<>();
    }

    /**
     * Adiciona um pedido expresso.
     *
     * @param pedidoExpresso o pedido expresso.
     */
    public void adicionaPedidoExpresso(UUID idEquipamento, String descricao) {
        pedidos.add(new PedidoExpresso(idEquipamento, descricao));
    }

    /**
     * Obtem um pedido expresso.
     *
     * @return um pedido expresso.
     */
    public PedidoExpresso getPedido() {
        return pedidos.peek();
    }

    public boolean verificaDisponibilidadeServicoExpresso() {
        return pedidos.size() <= MAX_SIZE;
    }

    /**
     * Conclui a reparação.
     */
    public PedidoExpresso concluirReparacao() {
        return pedidos.remove();
    }
}