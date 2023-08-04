package lojaLN.pedidos.expresso;

import lojaLN.pedidos.Pedido;

import java.util.UUID;

public class PedidoExpresso extends Pedido {

    public PedidoExpresso(UUID idEquipamento, String descricao) {
        super(idEquipamento, descricao);
    }
}