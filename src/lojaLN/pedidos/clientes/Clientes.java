package lojaLN.pedidos.clientes;

import lojaLN.excepcoes.ClienteJaRegistadoException;
import lojaLN.excepcoes.ClienteNaoExisteException;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Clientes implements Serializable {

	/**
	 * Key: idCliente
	 * Value: Cliente
	 */
	Map<String, Cliente> clientes;

	public Clientes() {
		clientes = new HashMap<>();
	}

	/**
	 * Envia o orçamento realizado para um dado cliente.
	 * @param pedido um pedido de reparação.
	 * @param idCliente um identificador de cliente(NIF).
	 */
	public void enviaOrcamento(String idCliente, PedidoReparacao pedido) {
		clientes.get(idCliente).enviaOrcamento(pedido);
	}

	/**
	 * Notifica um dado cliente que a sua reparação expresso está concluída.
	 * @param pedido um pedido de reparação expresso.
	 * @param idCliente um identificador de cliente(NIF).
	 */
	public void enviaConclusaoExpresso(String idCliente, PedidoExpresso pedido) {
		clientes.get(idCliente).enviaConclusaoExpresso(pedido);
	}

	/**
	 * Regista um cliente.
	 * @param idCliente um identificador de cliente(NIF).
	 * @param nome o nome do cliente.
	 * @param email o email do cliente.
	 * @param contacto o contacto do cliente.
	 * @throws ClienteJaRegistadoException caso o cliente já esteja registado.
	 */
	public void registarCliente(String idCliente, String nome, String email, String contacto) throws ClienteJaRegistadoException {
		if (clientes.containsKey(idCliente)) throw new ClienteJaRegistadoException(idCliente);
		clientes.put(idCliente, new Cliente(idCliente, nome, email, contacto));
	}

}