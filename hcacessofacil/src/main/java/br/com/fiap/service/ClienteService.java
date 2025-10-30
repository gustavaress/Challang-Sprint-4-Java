package br.com.fiap.service;

import br.com.fiap.dao.ClienteDao;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    private ClienteDao clienteDao;

    public void cadastrarCliente(Cliente cliente) throws SQLException, CampoJaCadastrado {

        if (emailExiste(cliente.getEmail())) {
            throw new CampoJaCadastrado("E-mail já cadastrado!");
        }

        if (cpfExiste(cliente.getCpf())) {
            throw new CampoJaCadastrado("CPF já cadastrado!");
        }

        clienteDao.inserir(cliente);
    }

    public List<Cliente> listarClientes() throws SQLException {
        return clienteDao.listar();
    }

    public Cliente buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        return clienteDao.buscarPorId(id);
    }

    public void atualizarCliente(Cliente cliente) throws SQLException, EntidadeNaoEncontradaException {
        clienteDao.atualizar(cliente);
    }

    public void deletarCliente(int id) throws SQLException, EntidadeNaoEncontradaException {
        clienteDao.deletar(id);
    }

    private boolean emailExiste(String email) throws SQLException {
        try {
            clienteDao.buscarPorEmail(email);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            return false;
        }
    }

    private boolean cpfExiste(String cpf) throws SQLException {
        try {
            clienteDao.buscarPorCpf(cpf);
            return true;
        } catch (EntidadeNaoEncontradaException e) {
            return false;
        }
    }
}
