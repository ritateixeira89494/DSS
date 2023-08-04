package lojaLN.pedidos.equipamentos;

public class EquipamentoAEntregar extends Equipamento {
    private final float preco;

    public EquipamentoAEntregar(EquipamentoAEntregar equipamentoAEntregar) {
        super(equipamentoAEntregar.getId(), equipamentoAEntregar.getIdCliente());
        this.preco = equipamentoAEntregar.getPreco();
    }

    public EquipamentoAEntregar(Equipamento equipamento, float preco) {
        super(equipamento);
        this.preco = preco;
    }

    public float getPreco() {
        return preco;
    }
}
