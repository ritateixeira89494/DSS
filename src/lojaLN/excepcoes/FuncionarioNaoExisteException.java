package lojaLN.excepcoes;

public class FuncionarioNaoExisteException extends Exception {

    public FuncionarioNaoExisteException(String id) {
        super("Funcionário com o id: " + id + "não existe!");
    }
}
