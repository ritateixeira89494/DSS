package lojaLN.pedidos.clientes;

import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.io.Serializable;

public class Cliente implements Serializable {

    private final String idCliente;
    private String nome; // Faltava o nome aqui
    private String email;
    private String contacto;

    public Cliente(String idCliente, String nome, String email, String contacto) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.contacto = contacto;
    }

    /**
     * Envia o orçamento realizado para o cliente.
     *
     * @param pedido o pedido de reparação.
     */
    public void enviaOrcamento(PedidoReparacao pedido) {
        // TODO: Enviar o email aqui
    }

    /**
     * Notifica o cliente que a sua reparação expresso está concluída.
     *
     * @param pedido o pedido expresso.
     */
    public void enviaConclusaoExpresso(PedidoExpresso pedido) {
        // TODO: Enviar o SMS aqui
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}