package lojaLN.pedidos.reparacao;

import java.time.Duration;
import java.util.List;

public class Passo {

    private final String nome;
    private final Duration tempo;
    private final float custo;
    private final String descricao;
    private final List<Passo> subpassos;

    /**
     * Construtor.
     *
     * @param nome      o nome do passo.
     * @param tempo     o tempo do passo
     * @param custo     o custo do passo.
     * @param descricao a descrição do passo.
     * @param subpassos os subpassos do passo.
     */
    public Passo(String nome, Duration tempo, float custo, String descricao, List<Passo> subpassos) {
        this.nome = nome;
        this.tempo = tempo;
        this.custo = custo;
        this.descricao = descricao;
        this.subpassos = subpassos;
    }

    /**
     * Adiciona um subpasso.
     *
     * @param passo o passo.
     */
    public void adicionarSubpasso(Passo passo) {
        subpassos.add(passo);
    }

    public float getCusto() {
        if (subpassos.size() == 0) return custo;
        return (float) subpassos.stream().mapToDouble(Passo::getCusto).sum();
    }

    public Duration getTempo() {
        if (subpassos.size() == 0) return tempo;
        return subpassos.stream().reduce(Duration.ZERO, (acc, passo) -> acc.plus(passo.tempo), Duration::plus);
    }
}