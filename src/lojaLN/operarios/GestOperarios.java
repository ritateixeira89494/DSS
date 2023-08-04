package lojaLN.operarios;

import lojaDAO.OperariosDAO;
import lojaLN.excepcoes.FuncionarioNaoExisteException;
import lojaLN.excepcoes.OperarioNaoExisteException;
import lojaLN.excepcoes.PasswordInvalidaException;
import lojaLN.operarios.excepcoes.IdJaExisteException;
import lojaLN.operarios.excepcoes.NaoSePodeCriarNovosAdminException;
import lojaLN.operarios.excepcoes.TecnicoNaoExisteException;
import lojaLN.operarios.excepcoes.TipoInvalidoException;
import lojaLN.operarios.tipos.Admin;
import lojaLN.operarios.tipos.Funcionario;
import lojaLN.operarios.tipos.Gestor;
import lojaLN.operarios.tipos.Tecnico;
import lojaLN.pedidos.expresso.PedidoExpresso;
import lojaLN.pedidos.orcamento.PedidoOrcamento;
import lojaLN.pedidos.reparacao.PedidoReparacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class GestOperarios implements IGestOperarios, Serializable {

    private final Map<String, Operario> operarios;

    public GestOperarios() {
        operarios = new HashMap<>();
    }

    public static GestOperarios getInstance() {
        return (GestOperarios) OperariosDAO.getInstance();
    }

    @Override
    public Operario login(String id, String password) throws OperarioNaoExisteException, PasswordInvalidaException {
        if (!operarios.containsKey(id)) throw new OperarioNaoExisteException(id);
        Operario operario = operarios.get(id);
        if (!operario.getPassword().equals(password)) throw new PasswordInvalidaException();
        return operario;
    }

    @Override
    public void adicionarOperario(String id, String password, String tipoOperario) throws IdJaExisteException, NaoSePodeCriarNovosAdminException, TipoInvalidoException {
        if (operarios.containsKey(id)) throw new IdJaExisteException(id);
        Operario operario = switch (tipoOperario) {
            case "tecnico" -> new Tecnico(id, password);
            case "funcionario" -> new Funcionario(id, password);
            case "gestor" -> new Gestor(id, password);
            case "admin" -> new Admin(id, password); // throw new NaoSePodeCriarNovosAdminException();
            default -> throw new TipoInvalidoException(tipoOperario);
        };
        operarios.put(id, operario);
    }

    @Override
    public void removerOperario(String idOperario) throws OperarioNaoExisteException {
        if (operarios.remove(idOperario) == null) throw new OperarioNaoExisteException(idOperario);
    }

    private Tecnico tecnicoComId(String id) throws TecnicoNaoExisteException {
        Operario operario = operarios.get(id);
        if (!(operario instanceof Tecnico)) { // também verifica se é null
            throw new TecnicoNaoExisteException(id);
        }

        return (Tecnico) operario;
    }

    private Funcionario funcionarioComId(String id) throws FuncionarioNaoExisteException {
        Operario operario = operarios.get(id);
        if (!(operario instanceof Funcionario)) { // também verifica se é null
            throw new FuncionarioNaoExisteException(id);
        }

        return (Funcionario) operario;
    }

    @Override
    public void adicionarOrcamentoConcluido(String idTecnico, PedidoOrcamento pedido) throws TecnicoNaoExisteException {
        tecnicoComId(idTecnico).addOrcamentoConcluido(pedido);
    }

    @Override
    public void adicionarReparacaoConcluida(String idTecnico, PedidoReparacao pedido) throws TecnicoNaoExisteException {
        tecnicoComId(idTecnico).addReparacaoConcluida(pedido);
    }

    @Override
    public void adicionarReparacaoExpressoConcluida(String idTecnico, PedidoExpresso pedido) throws TecnicoNaoExisteException {
        tecnicoComId(idTecnico).addReparacaoExpressoConcluida(pedido);
    }

    @Override
    public void adicionarRececao(String idFuncionario) throws FuncionarioNaoExisteException {
        funcionarioComId(idFuncionario).incNRececoes();
    }

    @Override
    public void adicionarEntrega(String idFuncionario) throws FuncionarioNaoExisteException {
        funcionarioComId(idFuncionario).incNEntregas();
    }

    public List<Tecnico> getTecnicos() {
        Predicate<Operario> pred = op -> op instanceof Tecnico;
        return operarios.values().stream().filter(pred).map(t -> (Tecnico) t).toList();
    }

    public List<Funcionario> getFuncionarios() {
        Predicate<Operario> pred = op -> op instanceof Funcionario;
        return operarios.values().stream().filter(pred).map(f -> (Funcionario) f).toList();
    }
}