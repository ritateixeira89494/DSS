package lojaLN.pedidos.reparacao;

import lojaLN.pedidos.Pedido;
import lojaLN.pedidos.orcamento.PedidoOrcamento;

import java.time.Duration;

public class PedidoReparacao extends Pedido {

    /**
     * Plano de trabalhos que o técnico seguir para reparar o equipamento.
     */
    private final PlanoTrabalhos planoTrabalhos;

    /**
     * O próximo passo a reparar. Necessário quando a reparação é interrompida
     */
    private int passoAtual;

    /**
     * Estado do pedido reparação.
     */
    private Estado estado;

    /**
     * Prazo da reparação.
     */
    private Duration duracao;

    /**
     * Custo atual do pedido de reparação.
     */
    private float custoAtual;

    /**
     * Constructor
     *
     * @param pedidoOrcamento um pedido de orçamento.
     * @param planoTrabalhos  um plano de trabalhos.
     */
    public PedidoReparacao(PedidoOrcamento pedidoOrcamento, PlanoTrabalhos planoTrabalhos) {
        super(pedidoOrcamento);
        this.planoTrabalhos = planoTrabalhos;
        this.passoAtual = 0;
        this.estado = Estado.EmEspera;
        this.duracao = Duration.ZERO;
        this.custoAtual = 0;
    }

    /**
     * Retorna o custo atual.
     *
     * @return custo atual.
     */
    public float getCustoAtual() {
        return custoAtual;
    }

    /**
     * Adiciona que um passo da reparação foi concluído. É sempre o próximo passo, porque não faz sentido saltar passos. Por isso, incrementa o passo atual.
     *
     * @param tempo o tempo de reparação em minutos.
     * @param custo o custo da reparação.
     */
    public void concluirPasso(int tempo, float custo) {
        if (passoAtual >= planoTrabalhos.getNumeroPassos()) return; // Já concluido
        passoAtual++;
        custoAtual += custo;
        duracao = duracao.plusMinutes(tempo);
    }


    /**
     * Avalia se o orçamento foi ultrapassado.
     *
     * @return true se o orçamento for ultrapassado.
     */
    public boolean orcamentoUltrapassado() {
        return custoAtual >= 1.2f * planoTrabalhos.getCustoTotal();
    }

    /**
     * Torna o pedido como aceite.
     */
    public void aceitarPedido() {
        this.estado = Estado.Aceite;
    }


    /**
     * Torna o pedido como recusado.
     */
    public void recusarPedido() {
        this.estado = Estado.Recusado;
    }


    /**
     * Retorna o estado do pedido.
     *
     * @return o estado do pedido.
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Diz se o pedido está concluído.
     *
     * @return true se o pedido estiver concluído.
     */
    public boolean estaConcluido() {
        return planoTrabalhos.getNumeroPassos() == passoAtual;
    }

    /**
     * Retorna o plano de trabalhos.
     *
     * @return o plano de trabalhos.
     */
    public PlanoTrabalhos getPlanoTrabalhos() {
        return planoTrabalhos;
    }

    /**
     * Retorna a duração atual do pedido de reparação.
     * @return a duração atual.
     */
    public Duration getDuracao() {
        return duracao;
    }

    /**
     * Retorna a duração prevista.
     * @return a duração prevista.
     */
    public Duration getDuracaoPrevista() {
        return planoTrabalhos.calculaDuracao();
    }
}