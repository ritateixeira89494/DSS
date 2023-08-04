package lojaLN.excepcoes;

public class OperarioNaoExisteException extends Exception {

    public OperarioNaoExisteException(String id) {
        super("Operario com o id: " + id + " não existe!");
    }
}
