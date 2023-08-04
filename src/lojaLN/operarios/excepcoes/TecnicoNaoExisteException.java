package lojaLN.operarios.excepcoes;

public class TecnicoNaoExisteException extends Exception {

    public TecnicoNaoExisteException(String id) {
        super("Tecnico com o id: " + id + "não existe!");
    }
}
