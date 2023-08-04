package lojaLN.excepcoes;

public class ClienteJaRegistadoException extends Exception {
    public ClienteJaRegistadoException(String idCliente) {
        super("Cliente com o id: " + idCliente + " já registado.");
    }
}
