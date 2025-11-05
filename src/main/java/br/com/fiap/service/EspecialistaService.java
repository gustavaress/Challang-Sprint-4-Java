package br.com.fiap.service;

import br.com.fiap.dao.EspecialistaDao;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Especialista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class EspecialistaService {

    @Inject
    private EspecialistaDao especialistaDao;

    public void cadastrarEspecialista(Especialista especialista) throws SQLException, CampoJaCadastrado {

        if (emailExiste(especialista.getEmail())) {
            throw new CampoJaCadastrado("E-mail já cadastrado!");
        }

        if (cpfExiste(especialista.getCpf())) {
            throw new CampoJaCadastrado("CPF já cadastrado!");
        }

        if (crmExiste(especialista.getCrm())) {
            throw new CampoJaCadastrado("CRM já cadastrado!");
        }

        especialistaDao.inserir(especialista);
    }

    public List<Especialista> listarEspecialistas() throws SQLException {
        return especialistaDao.listar();
    }

    public Especialista buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        return especialistaDao.buscarPorId(id);
    }

    public void atualizarEspecialista(Especialista especialista) throws SQLException, EntidadeNaoEncontradaException {
        especialistaDao.atualizar(especialista);
    }

    public void deletarEspecialista(int id) throws SQLException, EntidadeNaoEncontradaException {
        especialistaDao.deletar(id);
    }

    private boolean emailExiste(String email) throws SQLException {
        try {
            especialistaDao.buscarPorEmail(email);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            return false;
        }
    }

    private boolean cpfExiste(String cpf) throws SQLException {
        try {
            especialistaDao.buscarPorCpf(cpf);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            return false;
        }
    }

    private boolean crmExiste(String crm) throws SQLException {
        try {
            especialistaDao.buscarPorCrm(crm);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            return false;
        }
    }
}
