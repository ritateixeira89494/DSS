package lojaDAO;


import lojaLN.operarios.GestOperarios;
import lojaLN.operarios.IGestOperarios;

import java.io.*;

public class OperariosDAO {
    private final static String operariosFicheiro = "operarios.db";

    // Queremos controlar nós a criação dos objetos
    private OperariosDAO() {
    }

    /**
     * Obter uma instância da classe GestPedidos.
     *
     * @return uma instância do GestPedidos do ficheiro ou uma instância vazia.
     */
    public static IGestOperarios getInstance() {
        try {
            FileInputStream fis = new FileInputStream(operariosFicheiro);
            ObjectInputStream ois = new ObjectInputStream(fis);
            IGestOperarios gestOperarios = (GestOperarios) ois.readObject();
            ois.close();
            return gestOperarios;
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Algo ocorreu mal a carregar a base de dados! A inicializar os operarios!");
        }
        IGestOperarios gestOperarios = new GestOperarios();

        try {
            gestOperarios.adicionarOperario("admin", "admin", "admin"); // Adicionar um admin
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return gestOperarios;
    }

    public static void saveInstance(IGestOperarios gestOperarios) {
        try {
            FileOutputStream fos = new FileOutputStream(operariosFicheiro);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gestOperarios);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
