package lojaLN.pedidos.equipamentos;

import lojaLN.excepcoes.ClienteNaoExisteException;
import lojaLN.excepcoes.EquipamentoNaoExisteException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Equipamentos implements Serializable {

    /**
     * Key: idCliente
     * Value: Equipamentos do cliente
     */
    private final Map<String, Set<Equipamento>> equipamentos;

    /**
     * Key: idCliente
     * Value: Equipamentos do cliente
     */
    private final Map<String, Set<EquipamentoAEntregar>> equipamentosAEntregar;
    private final Set<Equipamento> equipamentosAbandonados;

    public Equipamentos() {
        equipamentos = new HashMap<>();
        equipamentosAEntregar = new HashMap<>();
        equipamentosAbandonados = new HashSet<>();
    }

    /**
     * @param idCliente
     */
    public Set<Equipamento> getEquipamentos(String idCliente) {
        return equipamentos.get(idCliente);
    }


    public Set<EquipamentoAEntregar> getEquipamentosAEntregar(String idCliente) {
        var equips = equipamentosAEntregar.get(idCliente);
        if (equips == null) return new HashSet<>();
        return equipamentosAEntregar.get(idCliente).stream().map(EquipamentoAEntregar::new).collect(Collectors.toSet());
    }

    /**
     * Regista um equipamento.
     *
     * @param idCliente o id do cliente.
     */
    public UUID registarEquipamento(String idCliente) {
        Equipamento equipamento = new Equipamento(idCliente);
        Set<Equipamento> equipamentosCliente =
                equipamentos.computeIfAbsent(equipamento.getIdCliente(), k -> new HashSet<>());
        equipamentosCliente.add(equipamento);
        return equipamento.getId();
    }

    /**
     * Abandona um equipamento.
     *
     * @param equipamento o equipamento.
     */
    public void abandonarEquipamento(Equipamento equipamento)
            throws ClienteNaoExisteException, EquipamentoNaoExisteException {

        removerEquipamento(equipamento);
        equipamentosAbandonados.add(equipamento);
    }

    /**
     * Entrega um equipamento.
     *
     * @param equipamento o equipamento.
     */
    public void levantarEquipamento(EquipamentoAEntregar equipamento) throws ClienteNaoExisteException, EquipamentoNaoExisteException {
        removerEquipamento(equipamento);
    }

    /**
     * Quando é chamada, tem de indicar qual o valor a cobrar ao cliente. No caso de ele recusar o orçamento, o valor é 0.
     *
     * @param equipamento o equipamento.
     * @param custo       o custo do equipamento.
     */
    public void equipamentoProntoEntregar(Equipamento equipamento, float custo) throws ClienteNaoExisteException, EquipamentoNaoExisteException {

        removerEquipamento(equipamento);
        EquipamentoAEntregar equipamentoAEntregar = new EquipamentoAEntregar(equipamento, custo);

        Set<EquipamentoAEntregar> equipamentosAEntregarCliente = equipamentosAEntregar.computeIfAbsent(equipamentoAEntregar.getIdCliente(), k -> new HashSet<>());
        equipamentosAEntregarCliente.add(equipamentoAEntregar);
    }

    /**
     * Remove um equipamento não entregue.
     *
     * @param equipamento o equipamento.
     * @throws ClienteNaoExisteException     caso o cliente não exista.
     * @throws EquipamentoNaoExisteException caso o equipamento não exista.
     */
    private void removerEquipamento(Equipamento equipamento) throws ClienteNaoExisteException, EquipamentoNaoExisteException {

        Set<Equipamento> equipamentosCliente = equipamentos.get(equipamento.getIdCliente());

        if (equipamentosCliente == null) {
            throw new ClienteNaoExisteException();
        }

        if (!equipamentosCliente.remove(equipamento)) {
            throw new EquipamentoNaoExisteException();
        }
    }

    /**
     * Remove um equipamento a entregar.
     *
     * @param equipamento o equipamento.
     * @throws ClienteNaoExisteException     caso o cliente não exista.
     * @throws EquipamentoNaoExisteException caso o equipamento não exista.
     */
    private void removerEquipamento(EquipamentoAEntregar equipamento) throws ClienteNaoExisteException, EquipamentoNaoExisteException {

        Set<EquipamentoAEntregar> equipamentosCliente = equipamentosAEntregar.get(equipamento.getIdCliente());

        if (equipamentosCliente == null) {
            throw new ClienteNaoExisteException();
        }

        if (!equipamentosCliente.remove(equipamento)) {
            throw new EquipamentoNaoExisteException();
        }
    }

    // /**
    //  * Recebe uma lista de equipamentos e vê quais podem ser entregues, e devolve a soma dos custos dos equipamentos filtrados.
    //  *
    //  * @param equipamentos
    //  */
    // private int calculaValorEntrega(Equipamento[] equipamentos) {
    //     // TODO - implement loja.pedidos.equipamentos.GestEquipamentos.calculaValorEntrega
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Deve retirar os equipamentos pagos, porque foram levantados
    //  *
    //  * @param equipamentosPagos
    //  */
    // private void assinalaPagamento(Equipamento[] equipamentosPagos) {
    //     // TODO - implement loja.pedidos.equipamentos.GestEquipamentos.assinalaPagamento
    //     throw new UnsupportedOperationException();
    // }

}