package lojaLN.operarios;

import lojaLN.excepcoes.FuncionarioNaoExisteException;
import lojaLN.excepcoes.OperarioNaoExisteException;
import lojaLN.excepcoes.PasswordInvalidaException;
import lojaLN.operarios.excepcoes.IdJaExisteException;
import lojaLN.operarios.excepcoes.NaoSePodeCriarNovosAdminException;
import lojaLN.operarios.excepcoes.TecnicoNaoExisteException;
import lojaLN.operarios.excepcoes.TipoInvalidoException;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Tecnico;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.util.List;

public interface IGestOperarios {

    /**
     * Login de um Operario.
     *
     * @param id       o id do operario.
     * @param password a password do operario.
     * @return o operario.
     * @throws OperarioNaoExisteException não existe o operario.
     * @throws PasswordInvalidaException  a password é inválida.
     */
    Operario login(String id, String password) throws OperarioNaoExisteException, PasswordInvalidaException;

    // ---------------------------- Operações Admin

    void adicionarOperario(String id, String password, String tipoOperario) throws IdJaExisteException, NaoSePodeCriarNovosAdminException, TipoInvalidoException;

    void removerOperario(String idOperario) throws OperarioNaoExisteException;

    // ---------------------------- Operações Funcionário

    void adicionarRececao(String idFuncionario) throws FuncionarioNaoExisteException;

    void adicionarEntrega(String idFuncionario) throws FuncionarioNaoExisteException;

    // ---------------------------- Operações Técnico

    void adicionarOrcamentoConcluido(String idTecnico, PedidoOrcamento pedido) throws TecnicoNaoExisteException;

    void adicionarReparacaoConcluida(String idTecnico, PedidoReparacao pedido) throws TecnicoNaoExisteException;

    void adicionarReparacaoExpressoConcluida(String idTecnico, PedidoExpresso pedido) throws TecnicoNaoExisteException;

    // ---------------------------- Operações Gestor
    List<Tecnico> getTecnicos();

    List<Funcionario> getFuncionarios();
}