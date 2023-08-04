package lojaUI.menus;

import lojaLN.GestLoja;
import lojaLN.IGestLoja;
import lojaLN.excepcoes.OperarioNaoExisteException;
import lojaLN.excepcoes.PasswordInvalidaException;
import lojaLN.operarios.Operario;
import lojaLN.operarios.tipos.Admin;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Gestor;
import lojaLN.operarios.tipos.Tecnico;

import java.util.Scanner;

/**
 * Exemplo de interface em modo texto.
 *
 * @author JFC
 * @version 20210930
 */
public class menuPrincipal {
    // O model tem a 'lógica de negócio'.
    private final IGestLoja model;


    // Scanner para leitura
    private final Scanner scin;

    /**
     * Construtor.
     * <p>
     * Cria os menus e a camada de negócio.
     */
    public menuPrincipal() {

        this.model = new GestLoja();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Centro de Reparações!");
        this.menuPrincipal();
        this.model.guardar();
        System.out.println("Até breve...");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     * <p>
     * Transições para:
     * Operações sobre Alunos
     * Operações sobre Turmas
     * Adicionar Aluno a Turma
     * Remover Aluno de Turma
     * Listar Alunos de Turma
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Autentificação do Operário"});

        // Registar os handlers das transições
        menu.setHandler(1, this::login);

        // Executar o menu
        menu.run();
    }


    /**
     * Estado - Login
     */
    private void login() {
        try {
            System.out.println("Identificador do Operário: ");
            String id = scin.nextLine();
            System.out.println("Password do Operário: ");
            String pass = scin.nextLine();

            try {
                redirecionarMenu(model.login(id, pass));
            } catch (OperarioNaoExisteException | PasswordInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void redirecionarMenu(Operario operario) {
        String id = operario.getId();
        if (operario instanceof Funcionario) new MenuFuncionario(model, scin, id);
        else if (operario instanceof Tecnico) new MenuTecnico(model, scin, id);
        else if (operario instanceof Gestor) new MenuGestor(model, scin, id);
        else if (operario instanceof Admin) new MenuAdmin(model, scin, id);
    }

}
