package lojaLN.pedidos;

import java.io.Serializable;
import java.util.UUID;

public abstract class Pedido implements Serializable {

    private final UUID idEquipamento;
    private final String descricao;

    protected Pedido(UUID idEquipamento, String descricao) {
        this.idEquipamento = idEquipamento;
        this.descricao = descricao;
    }

    protected Pedido(Pedido pedido) {
        this.idEquipamento = pedido.idEquipamento;
        this.descricao = pedido.descricao;
    }

    public UUID getIdEquipamento() {
        return idEquipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Pedido: " +
                "idEquipamento=" + idEquipamento +
                ", descricao=" + descricao;
    }
}