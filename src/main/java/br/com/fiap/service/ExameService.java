package br.com.fiap.service;

import br.com.fiap.dao.ExameDao;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Exame;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ExameService {

    @Inject
    private ExameDao exameDao;

    // ✅ Cadastrar Exame
    public void cadastrarExame(Exame exame) throws SQLException {
        exameDao.inserir(exame);
    }

    // ✅ Listar todos os exames
    public List<Exame> listarExames() throws SQLException {
        return exameDao.listar();
    }

    // ✅ Buscar exame por ID
    public Exame buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        return exameDao.buscarPorId(id);
    }

    // ✅ Atualizar exame existente
    public void atualizarExame(Exame exame) throws SQLException, EntidadeNaoEncontradaException {
        exameDao.atualizar(exame);
    }

    // ✅ Deletar exame
    public void deletarExame(int id) throws SQLException, EntidadeNaoEncontradaException {
        exameDao.deletar(id);
    }
}
