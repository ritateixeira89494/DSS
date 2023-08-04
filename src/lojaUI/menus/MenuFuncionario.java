package lojaUI.menus;

import lojaLN.IGestLoja;
import lojaLN.pedidos.equipamentos.EquipamentoAEntregar;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.out;

public class MenuFuncionario {

    private final IGestLoja model;
    private final Scanner scin;
    private final String idFuncionario;

    public MenuFuncionario(IGestLoja model, Scanner scin, String idFuncionario) {
        this.model = model;
        this.scin = scin;
        this.idFuncionario = idFuncionario;

        Menu menu = new Menu("MenuFuncionário", new String[]{
                "Registar Cliente",
                "Registar Pedido de Orçamento",
                "Registar Pedido de Reparação",
                "Registar Levantamento de Equipamento",
                "Registar Pedido de Serviço Expresso"
        });

        menu.setHandler(1, this::registarCliente);
        menu.setHandler(2, this::registarPedidoOrcamento);
        menu.setHandler(3, this::registarPedidoReparacao);
        menu.setHandler(4, this::registarLevantamentoEquipamento);
        menu.setHandler(5, this::registarPedidoServicoExpresso);

        menu.run();
    }

    public void registarCliente() {
        try {
            System.out.println("Dados a inserir acerca do cliente: ");

            System.out.println("NIF do cliente: ");
            String nif = scin.nextLine();

            System.out.println("Nome do cliente: ");
            String n = scin.nextLine();

            System.out.println("Email do cliente: ");
            String e = scin.nextLine();

            System.out.println("Contacto do Cliente: ");
            String c = scin.nextLine();

            model.registarCliente(nif, n, e, c);
            System.out.println("O registo do cliente foi realizado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Aconteceu algo de errado! Por favor tente novamente!");
        }
    }

    public void registarPedidoOrcamento() {
        try {
            System.out.println("Indique o ID do cliente: ");
            String id = scin.nextLine();

            System.out.println("Descreva o problema que o equipamento apresenta: ");
            String prob = scin.nextLine();

            model.registarPedidoOrcamento(idFuncionario, id, prob);
            System.out.println("O pedido de orçamento foi realizado com sucesso!");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }


    public void registarPedidoReparacao() {
        try {
            //TODO: mostrar emails
            System.out.println("Indique o ID do Equipamento: ");
            UUID idEquipamento = UUID.fromString(scin.nextLine());
            System.out.println("Indique o ID do Cliente: ");
            String idCliente = scin.nextLine();
            System.out.println("O pedido de reparação foi aceite?" + " Y/N");
            String res = scin.next();
            PedidoReparacao pedidoReparacao = model.getPedidoReparacao(idEquipamento);
            if (!res.equalsIgnoreCase("Y")) {
                pedidoReparacao.recusarPedido();
                model.enviarPedidoRecolhaEquipamento(idEquipamento);
            } else {
                pedidoReparacao.aceitarPedido();
            }
        } catch (IllegalArgumentException e) {
            out.println("Id do equipamento é inválido!");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }


    public void registarLevantamentoEquipamento() {
        try {
            System.out.println("Indique o NIF do cliente: ");
            String nif = scin.nextLine();

            var equipamentos = model.getEquipamentosAEntregar(nif).stream().toList();
            if (equipamentos.size() == 0) {
                out.println("Sem equipamentos a entregar!");
                return;
            }

            out.println("Equipamentos a entregar: ");
            while (equipamentos.size() > 0) {

                for (int i = 0; i < equipamentos.size(); i++) out.println(i + ". " + equipamentos.get(i));

                int num = Integer.parseInt(scin.nextLine());
                EquipamentoAEntregar equipamento = equipamentos.get(num);
                out.println("Preço da reparação: " + equipamento.getPreco());

                System.out.println("A reparação foi paga?" + " Y/N");
                String res = scin.next();
                if (!res.equalsIgnoreCase("Y")) {
                    equipamentos.remove(num);
                }
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    public void registarPedidoServicoExpresso() {
        try {
            System.out.println("A reparação será agora encaminhada.");
            System.out.println("Indique o ID do cliente: ");
            String id = scin.nextLine();

            System.out.println("Descreva o problema que o equipamento apresenta: ");
            String prob = scin.nextLine();

            model.registarPedidoExpresso(id, prob);
            System.out.println("Equipamento registado com sucesso.");

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

}
