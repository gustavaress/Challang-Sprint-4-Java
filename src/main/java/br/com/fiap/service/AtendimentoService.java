package br.com.fiap.service;

import br.com.fiap.dao.AtendimentoDao;
import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dao.EspecialistaDao;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Atendimento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class AtendimentoService {

    @Inject
    private AtendimentoDao atendimentoDao;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private EspecialistaDao especialistaDao;

    public void cadastrarAtendimento(Atendimento atendimento) throws Exception {

        clienteDao.buscarPorId(atendimento.getCliente().getCodigo());
        especialistaDao.buscarPorId(atendimento.getEspecialista().getCodigo());

        atendimentoDao.inserir(atendimento);
    }

    public List<Atendimento> listarAtendimentos() throws SQLException {
        return atendimentoDao.listar();
    }

    public Atendimento buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        return atendimentoDao.buscarPorId(id);
    }

    public void atualizarAtendimento(Atendimento atendimento) throws SQLException, EntidadeNaoEncontradaException {

        clienteDao.buscarPorId(atendimento.getCliente().getCodigo());
        especialistaDao.buscarPorId(atendimento.getEspecialista().getCodigo());

        atendimentoDao.atualizar(atendimento);
    }

    public void deletarAtendimento(int id) throws SQLException, EntidadeNaoEncontradaException {
        atendimentoDao.deletar(id);
    }
}
