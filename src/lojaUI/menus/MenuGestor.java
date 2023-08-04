package lojaUI.menus;

import lojaLN.IGestLoja;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Tecnico;

import java.util.Scanner;

public class MenuGestor {
    private final IGestLoja model;
    private final Scanner scin;
    private final String idGestor;

    public MenuGestor(IGestLoja model, Scanner scin, String idGestor) {
        this.model = model;
        this.scin = scin;
        this.idGestor = idGestor;

        Menu menu = new Menu("MenuGestor", new String[] {
            "Consultar estatísticas dos operários"
        });

        menu.setHandler(1, this::consultarInformacoesOperarios);

        menu.run();
    }

    private void consultarInformacoesOperarios() {
        var funcionarios = model.getFuncionarios();
        var tecnicos = model.getTecnicos();

        for (Funcionario funcionario:funcionarios) System.out.println(funcionario.getInformacoes());

        for (Tecnico tecnico:tecnicos) System.out.println(tecnico.getInformacoes());
    }
}
