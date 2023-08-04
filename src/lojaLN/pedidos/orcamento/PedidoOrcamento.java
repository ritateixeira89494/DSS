package lojaLN.pedidos.orcamento;

import lojaLN.pedidos.Pedido;

import java.io.Serializable;
import java.util.UUID;

public class PedidoOrcamento extends Pedido {

	public PedidoOrcamento(UUID idEquipamento, String descricao) {
		super(idEquipamento, descricao);
	}
}