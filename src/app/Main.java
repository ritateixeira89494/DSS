package app;

import lojaUI.menus.menuPrincipal;

public class Main {

    public static void main(String[] args) {
        try {
            new menuPrincipal().run();
        } catch (Exception e) {
            System.out.println("Erro Fatal: " + e.getMessage() + " [" + e + "]");
        }
    }
}
