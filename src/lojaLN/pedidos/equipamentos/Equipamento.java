package lojaLN.pedidos.equipamentos;

import java.io.Serializable;
import java.util.UUID;

public class Equipamento implements Serializable {
	private final UUID id;
	private final String idCliente;

	public Equipamento(String idCliente) {
		this.id = UUID.randomUUID();
		this.idCliente = idCliente;
	}

	protected Equipamento(UUID id, String idCliente) {
		this.id = id;
		this.idCliente = idCliente;
	}

	public Equipamento(Equipamento equipamento) {
		this.id = equipamento.id;
		this.idCliente = equipamento.idCliente;
	}

	public UUID getId() {
		return id;
	}

	public String getIdCliente() {
		return idCliente;
	}
}