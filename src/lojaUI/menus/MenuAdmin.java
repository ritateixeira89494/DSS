package lojaUI.menus;

import lojaLN.IGestLoja;
import lojaLN.excepcoes.OperarioNaoExisteException;
import lojaLN.operarios.excepcoes.IdJaExisteException;
import lojaLN.operarios.excepcoes.NaoSePodeCriarNovosAdminException;
import lojaLN.operarios.excepcoes.TipoInvalidoException;

import java.util.Scanner;

import static java.lang.System.out;

public class MenuAdmin {
    private final IGestLoja model;
    private final Scanner scin;
    private final String idAdmin;

    public MenuAdmin(IGestLoja model, Scanner scin, String idAdmin) {
        this.model = model;
        this.scin = scin;
        this.idAdmin = idAdmin;

        Menu menu = new Menu("MenuAdmin", new String[]{
                "Adicionar Operario",
                "Remover operario"
        });

        // menu.setPreCondition(1, model.haPedidosOrcamento);
        // menu.setPreCondition(2, model.haPedidosReparacaoRegular);
        // menu.setPreCondition(3, model.haPedidosReparacaoExpresso);

        menu.setHandler(1, this::adicionarOperario);
        menu.setHandler(2, this::removerOperario);

        menu.run();
    }

    private void adicionarOperario() {
        // Id do operario
        out.println("Id do operário a adicionar: ");
        String id = scin.nextLine();

        // Password
        out.println("Password: ");
        String password = scin.nextLine();

        // Tipo
        out.println("Tipo: [funcionario|tecnico|gestor] ");
        String tipo = scin.nextLine();

        try {
            model.adicionarOperario(id, password, tipo.toLowerCase());
        } catch (IdJaExisteException | NaoSePodeCriarNovosAdminException | TipoInvalidoException e) {
            out.println(e.getMessage());
        }
    }

    private void removerOperario() {
        // Id do operario
        out.println("Id do operário a remover: ");
        String id = scin.nextLine();

        try {
            model.removerOperario(id);
        } catch (OperarioNaoExisteException e) {
            out.println(e.getMessage());
        }
    }
}
