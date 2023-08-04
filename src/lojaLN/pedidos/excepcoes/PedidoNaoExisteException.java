package lojaLN.pedidos.excepcoes;

import java.util.UUID;

public class PedidoNaoExisteException extends Exception {

    public PedidoNaoExisteException(UUID idEquipamento) {
        super("Pedido com o equipamento id: " + idEquipamento + "não existe.");
    }
}
