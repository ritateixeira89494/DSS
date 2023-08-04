package lojaLN.pedidos.excepcoes;

import lojaLN.pedidos.Pedido;

import java.util.UUID;

public class PedidoJaExisteException extends Exception {

    public PedidoJaExisteException(Pedido pedido) {
        super(pedido + " já existe.");
    }
}
