package lojaUI.menus;

import lojaLN.IGestLoja;
import lojaLN.pedidos.Pedido;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.Passo;
import lojaLN.pedidos.reparacao.PedidoReparacao;
import lojaLN.pedidos.reparacao.PlanoTrabalhos;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class MenuTecnico {
    private final IGestLoja model;
    private final Scanner scin;
    private final String idTecnico;

    public MenuTecnico(IGestLoja model, Scanner scin, String idTecnico) {
        this.model = model;
        this.scin = scin;
        this.idTecnico = idTecnico;

        Menu menu = new Menu("MenuTécnico", new String[]{"Realizar Orçamento de Equipamento", "Reparar Equipamento de Serviço Regular", "Reparar Equipamento de Serviço Expresso"});

        // menu.setPreCondition(1, model.haPedidosOrcamento);
        // menu.setPreCondition(2, model.haPedidosReparacaoRegular);
        // menu.setPreCondition(3, model.haPedidosReparacaoExpresso);

        menu.setHandler(1, this::realizarOrcamentoEquipamento);
        menu.setHandler(2, this::repararEquipamentoServicoRegular);
        menu.setHandler(3, this::repararEquipamentoExpresso);

        menu.run();
    }


    private void realizarOrcamentoEquipamento() {
        try {
            var pedidos = model.getPedidosOrcamentoPendentes();
            if (pedidos.length == 0) {
                out.println("Sem pedidos de orçamento pendentes!");
                return;
            }

            out.println("Pedidos de orçamento pendentes: ");
            mostraPedidos(pedidos);

            out.println("Selecione o número do pedido.");
            PedidoOrcamento pedidoOrcamento = pedidos[(Integer.parseInt(scin.nextLine()))];

            out.println("Código do equipamento: " + pedidoOrcamento.getIdEquipamento());

            out.println("Pretende iniciar um novo plano de trabalhos? Y/N");
            String res = scin.next();
            if (!res.equalsIgnoreCase("Y")) return;

            PlanoTrabalhos plano = new PlanoTrabalhos();
            out.println("Novo plano de trabalhos criado!");

            out.println("Procedemos para a adição de passos...");


            while (true) {
                out.println("Pretende adicionar mais um passo ao plano de trabalhos?" + " Y/N");
                res = scin.next();
                if (!res.equalsIgnoreCase("Y")) break;
                plano.adicionaPasso(adicionarPasso());
            }

            out.println("Orçamento de pedido realizado!");

            model.concluirPedidoOrcamento(idTecnico, pedidoOrcamento.getIdEquipamento());

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    private void mostraPedidos(Pedido[] pedidos) {
        for (int i = 0; i < pedidos.length; i++) {
            out.println(i + ". " + pedidos[i]);
        }
    }

    private Passo adicionarPasso() {
        // Nome
        out.println("Nome do passo a adicionar: ");
        String nome = scin.nextLine();

        // Duração
        out.println("Duração do passo em minutos:");
        Duration duracao = Duration.ofMinutes(Integer.parseInt(scin.nextLine()));

        // Custo
        out.println("Custo do passo: ");
        float custo = Float.parseFloat(scin.nextLine());

        // Descrição
        out.println("Descrição do passo: ");
        String descricao = scin.nextLine();

        // Criação do passo
        List<Passo> subpassos = new ArrayList<>();
        Passo passo = new Passo(nome, duracao, custo, descricao, subpassos);

        // Adição de subpassos
        while (true) {
            out.println("Pretende adicionar mais um subpasso? Y/N");
            String res = scin.next();
            if (!res.equalsIgnoreCase("Y")) break;
            subpassos.add(adicionarPasso());
        }
        out.println("Passo adicionado com sucesso!");

        return passo;
    }

    private void repararEquipamentoServicoRegular() {
        try {
            var pedidos = model.getPedidosReparacao();
            if (pedidos.size() == 0) {
                out.println("Sem pedidos de reparação pendentes!");
                return;
            }

            out.println("Escolha um dos seguintes pedidos de reparação aceites: ");
            for (int i = 0; i < pedidos.size(); i++) out.println(i + ". " + pedidos.get(i));

            PedidoReparacao pedidoReparacao = pedidos.get(Integer.parseInt(scin.nextLine()));
            var passos = pedidoReparacao.getPlanoTrabalhos().getPassos();

            out.println("Seguem-se os passos de reparação: ");

            for (int i = 0; i < passos.size(); ) {
                out.println(passos.get(i));
                out.println("Para indicar como concluido diga F \nPara voltar atrás A");
                String res = scin.next();
                if (res.equalsIgnoreCase("F")) {
                    i++;
                } else if (res.equalsIgnoreCase("A")) i--;
            }

            if (pedidoReparacao.orcamentoUltrapassado());
                // model.notificarUltrapasseDePreco(pedidoReparacao.getIdEquipamento());//TODO: Metodo que mande o email meu;
            else {
                out.println("Equipamento registado como reparado!");
                model.enviarPedidoRecolhaEquipamento(pedidoReparacao.getIdEquipamento());
            }

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    private void registarPasso(PedidoReparacao pedidoReparacao) {
        // Duração
        out.println("Duração do passo em minutos:");
        int duracao = Integer.parseInt(scin.nextLine());

        // Custo
        out.println("Custo do passo: ");
        float custo = Float.parseFloat(scin.nextLine());

        pedidoReparacao.concluirPasso(duracao, custo);
    }

    private void repararEquipamentoExpresso() {
        try {
            var pedidoExpresso = model.proximoPedidoExpresso();
            if (pedidoExpresso == null) {
                out.println("Sem pedidos de reparação expresso pendentes!");
                return;
            }

            out.println("Código do equipamento a reparar: " + pedidoExpresso.getIdEquipamento());
            out.println("Descrição do pedido: " + pedidoExpresso.getDescricao());

            out.println("Por favor indique quando se der como concluida a reparação");
            String res = scin.nextLine();

            model.concluirReparacaoExpresso(idTecnico);
            out.println("Reparação registada como concluída!");

            model.notificarClienteSMS(pedidoExpresso.getIdEquipamento());
            out.println("Cliente notificado!");

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

}
