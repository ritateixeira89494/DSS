package lojaLN.operarios.tipos;

import lojaLN.operarios.Operario;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Operario {

    private final List<PedidoOrcamento> pedidosOrcamentos;
    private final List<PedidoReparacao> pedidosReparacao;
    private final List<PedidoExpresso> pedidosExpresso;

    /**
     * Duração média das reparações programadas.
     */
    private Duration duracaoMedia;
    /**
     * Desvio médio da duração em relação à duração prevista.
     * Estatística relativa às reparações programadas.
     */
    private Duration desvioMedioDuracaoPrevista;

    public Tecnico(String id, String password) {
        super(id, password);
        pedidosOrcamentos = new ArrayList<>();
        pedidosReparacao = new ArrayList<>();
        pedidosExpresso = new ArrayList<>();
    }

    public void addOrcamentoConcluido(PedidoOrcamento pedido) {
        pedidosOrcamentos.add(pedido);
    }

    public void addReparacaoConcluida(PedidoReparacao pedido) {
        pedidosReparacao.add(pedido);
        duracaoMedia = pedidosReparacao.stream()
                .reduce(Duration.ZERO, (acc, p) -> acc.plus(p.getDuracao()), Duration::plus)
                .dividedBy(pedidosReparacao.size());
        desvioMedioDuracaoPrevista = pedidosReparacao.stream()
                .reduce(Duration.ZERO, (acc, p) -> acc.plus(p.getDuracao().minus(p.getDuracaoPrevista())), Duration::plus)
                .dividedBy(pedidosReparacao.size());
    }

    public void addReparacaoExpressoConcluida(PedidoExpresso pedido) {
        pedidosExpresso.add(pedido);
    }

    /**
     * Descreve as informações de um funcionário.
     */
    public String getInformacoes() {
        return toString();
    }


    @Override
    public String toString() {
        return "Tecnico: " +
                " Duracão Média=" + duracaoMedia +
                ", Desvio Médio da Duracão Prevista=" + desvioMedioDuracaoPrevista +
                ", Pedidos Orcamentos=" + pedidosOrcamentos +
                ", Pedidos Reparacao=" + pedidosReparacao +
                ", Pedidos Expresso=" + pedidosExpresso;
    }
}