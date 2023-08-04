package lojaLN.pedidos.reparacao;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PlanoTrabalhos {

    /**
     * Passos da reparação.
     */
    private final List<Passo> passos;

    /**
     * Constructor
     */
    public PlanoTrabalhos() {
        this.passos = new ArrayList<>();
    }

    /**
     * Adiciona um passo num dado indice.
     *
     * @param indice o indice do passo.
     * @param passo  um passo.
     */
    public void adicionaPasso(int indice, Passo passo) {
        passos.add(indice, passo);
    }


    /**
     * Adicionar um passo ao final da lista de passos.
     *
     * @param passo um passo.
     */
    public void adicionaPasso(Passo passo) {
        passos.add(passo);
    }

    /**
     * Calcula a duração prevista de um plano de trabalhos.
     *
     * @return a duração prevista.
     */
    public Duration calculaDuracao() {
        return passos.stream().reduce(Duration.ZERO, (acc, passo) -> acc.plus(passo.getTempo()), Duration::plus);
    }

    /**
     * Retorna o número de passos do plano de trabalhos.
     *
     * @return o número de passos.
     */
    public int getNumeroPassos() {
        return passos.size();
    }

    /**
     * Retorna o custo total.
     *
     * @return o custo total.
     */
    public float getCustoTotal() {
        return (float) passos.stream().mapToDouble(Passo::getCusto).sum();
    }

    /**
     * Retorna a lista de passos.
     *
     * @return a lista de passos.
     */
    public List<Passo> getPassos() {
        return passos;
    }
}