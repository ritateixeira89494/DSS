package lojaLN.operarios.excepcoes;

public class TipoInvalidoException extends Exception {

    public TipoInvalidoException(String tipoOperario) {
        super("Tipo: " + tipoOperario + " é inválido!");
    }
}
