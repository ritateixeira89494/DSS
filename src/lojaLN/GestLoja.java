package lojaLN;

import lojaDAO.OperariosDAO;
import lojaDAO.PedidosDAO;
import lojaLN.excepcoes.*;
import lojaLN.operarios.GestOperarios;
import lojaLN.operarios.IGestOperarios;
import lojaLN.operarios.Operario;
import lojaLN.operarios.excepcoes.IdJaExisteException;
import lojaLN.operarios.excepcoes.NaoSePodeCriarNovosAdminException;
import lojaLN.operarios.excepcoes.TecnicoNaoExisteException;
import lojaLN.operarios.excepcoes.TipoInvalidoException;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Tecnico;
import lojaLN.pedidos.GestPedidos;
import lojaLN.pedidos.IGestPedidos;
import lojaLN.pedidos.equipamentos.Equipamento;
import lojaLN.pedidos.equipamentos.EquipamentoAEntregar;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GestLoja implements IGestLoja, Serializable {
    private final IGestOperarios gestOperarios;
    private final IGestPedidos gestPedidos;

    public GestLoja() {
        this.gestOperarios = GestOperarios.getInstance();
        this.gestPedidos = GestPedidos.getInstance();
    }

    public void guardar() {
        OperariosDAO.saveInstance(gestOperarios);
        PedidosDAO.saveInstance(gestPedidos);
    }

    @Override
    public Operario login(String id, String password) throws OperarioNaoExisteException, PasswordInvalidaException {
        return gestOperarios.login(id, password);
    }

    @Override
    public void registarCliente(String idCliente, String nome, String email, String contacto) throws ClienteJaRegistadoException {
        gestPedidos.registarCliente(idCliente, nome, email, contacto);
    }

    @Override
    public void registarPedidoOrcamento(String idFuncionario, String idEquipamento, String descricao)
            throws EquipamentoNaoExisteException, FuncionarioNaoExisteException {
        gestPedidos.registarPedidoOrcamento(idEquipamento, descricao);
        gestOperarios.adicionarRececao(idFuncionario);
    }

    @Override
    public PedidoOrcamento[] getPedidosOrcamentoPendentes() {
        return gestPedidos.getPedidosOrcamentoPendentes();
    }

    @Override
    public PedidoReparacao getPedidoReparacao(UUID idEquipamento) throws PedidoNaoExisteException {
        return gestPedidos.getPedidoReparacao(idEquipamento);
    }

    @Override
    public void redirecionarEmail(String email, String idFuncionario) throws FuncionarioNaoExisteException {
        gestPedidos.redirecionarEmail(email, idFuncionario);
    }

    @Override
    public void notificarUltrapasseDePreco(String idCliente, UUID idEquipamento) {
        gestPedidos.notificarUltrapasseDePreco(idCliente, idEquipamento);
    }

    @Override
    public void arquivarOrcamento(UUID idEquipamento) throws EquipamentoNaoExisteException {
        gestPedidos.arquivarOrcamento(idEquipamento);
    }

    @Override
    public List<PedidoReparacao> getPedidosReparacao() {
        return gestPedidos.getPedidosReparacao();
    }

    @Override
    public void enviarPedidoRecolhaEquipamento(UUID idEquipamento) throws EquipamentoNaoExisteException {
        gestPedidos.enviarPedidoRecolhaEnquipamento(idEquipamento);
    }

    @Override
    public void concluirPedidoOrcamento(String idTecnico, UUID idEquipamento) throws PedidoNaoExisteException, TecnicoNaoExisteException {

        PedidoOrcamento pedido = gestPedidos.removerPedidoOrcamento(idEquipamento);
        gestOperarios.adicionarOrcamentoConcluido(idTecnico, pedido);
    }

    @Override
    public UUID equipamentoEmPedidoOrcamento(PedidoOrcamento orcamento) {
        return gestPedidos.equipamentoEmPedidoOrcamento(orcamento);
    }

    @Override
    public void concluirReparacao(String idTecnico, Equipamento equipamento)
            throws PedidoNaoExisteException, ClienteNaoExisteException, EquipamentoNaoExisteException, TecnicoNaoExisteException {

        PedidoReparacao pedido = gestPedidos.repararEquipamento(equipamento);

        gestOperarios.adicionarReparacaoConcluida(idTecnico, pedido);
    }

    @Override
    public void enviarOrcamentoCliente(String idCliente) {
        gestPedidos.enviarOrcamentoCliente(idCliente);
    }

    @Override
    public void registarLevantamentoEquipamento(String idFuncionario, EquipamentoAEntregar equipamento)
            throws ClienteNaoExisteException, EquipamentoNaoExisteException, FuncionarioNaoExisteException {
        gestPedidos.levantarEquipamento(equipamento);
        gestOperarios.adicionarEntrega(idFuncionario);
    }

    @Override
    public void equipamentoAbandonado(Equipamento e) throws ClienteNaoExisteException, EquipamentoNaoExisteException {
        gestPedidos.equipamentoAbandonado(e);
    }

    @Override
    public Set<EquipamentoAEntregar> getEquipamentosAEntregar(String idCliente) {
        return gestPedidos.getEquipamentosAEntregar(idCliente);
    }

    @Override
    public void registarPedidoExpresso(String idCliente, String descricao) {
        gestPedidos.registarPedidoExpresso(idCliente, descricao);
    }

    @Override
    public boolean verificaDisponibilidadeServicoExpresso() {
        return gestPedidos.verificaDisponibilidadeServicoExpresso();
    }

    @Override
    public PedidoExpresso proximoPedidoExpresso() {
        return gestPedidos.proximoPedidoExpresso();
    }

    @Override
    public void concluirReparacaoExpresso(String idTecnico) throws TecnicoNaoExisteException {
        PedidoExpresso pedido = gestPedidos.concluirReparacaoExpresso();
        gestOperarios.adicionarReparacaoExpressoConcluida(idTecnico, pedido);
    }

    @Override
    public void notificarClienteSMS(UUID idEquipamento) {

    }

    // ---------------------------- Operações Admin

    @Override
    public void adicionarOperario(String id, String password, String tipoOperario) throws IdJaExisteException, NaoSePodeCriarNovosAdminException, TipoInvalidoException {
        gestOperarios.adicionarOperario(id, password, tipoOperario);
    }

    @Override
    public void removerOperario(String idOperario) throws OperarioNaoExisteException {
        gestOperarios.removerOperario(idOperario);
    }

    // ---------------------------- Operações Gestor


    @Override
    public List<Tecnico> getTecnicos() {
        return gestOperarios.getTecnicos();
    }

    @Override
    public List<Funcionario> getFuncionarios() {
        return gestOperarios.getFuncionarios();
    }
}