package lojaLN.operarios.excepcoes;

public class IdJaExisteException extends Exception {
    public IdJaExisteException(String id) {
        super("O id: " + id + " já existe.");
    }
}
