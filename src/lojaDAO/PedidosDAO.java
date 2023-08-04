package lojaDAO;

import lojaLN.pedidos.GestPedidos;
import lojaLN.pedidos.IGestPedidos;

import java.io.*;

public class PedidosDAO {
    private final static String pedidosFicheiro = "pedidos.db";

    // Queremos controlar nós a criação dos objetos
    private PedidosDAO() {
    }

    /**
     * Obter uma instância da classe GestPedidos.
     *
     * @return uma instância do GestPedidos do ficheiro ou uma instância vazia.
     */
    public static IGestPedidos getInstance() {
        try {
            FileInputStream fis = new FileInputStream(pedidosFicheiro);
            ObjectInputStream ois = new ObjectInputStream(fis);
            IGestPedidos gestPedidos  = (IGestPedidos) ois.readObject();
            ois.close();
            return gestPedidos;
        } catch (Exception e) {
            System.out.println("Algo ocorreu mal a carregar a base de dados! A inicializar os pedidos!");
        }
        return new GestPedidos();
    }

    public static void saveInstance(IGestPedidos gestPedidos) {
        try {
            FileOutputStream fos = new FileOutputStream(pedidosFicheiro);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gestPedidos);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
