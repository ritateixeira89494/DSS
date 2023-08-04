package lojaLN.excepcoes;

import java.util.UUID;

public class PedidoNaoExisteException extends Exception {
    public PedidoNaoExisteException(UUID idEquipamento) {
        super("Não existe pedido com o equipamento com o id: " + idEquipamento);
    }
}
