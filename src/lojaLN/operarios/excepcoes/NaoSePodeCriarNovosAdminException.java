package lojaLN.operarios.excepcoes;

public class NaoSePodeCriarNovosAdminException extends Exception {
    public NaoSePodeCriarNovosAdminException() {
        super("Não se pode criar novos adminstradores!");
    }
}
