package br.com.fiap.service;

import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dto.cliente.AtualizarClienteDto;
import br.com.fiap.dto.cliente.CadastroClienteDto;
import br.com.fiap.dto.cliente.ListarClienteDto;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService {

    @Inject
    private ClienteDao clienteDao;

    // =========================
    // CADASTRAR CLIENTE
    // =========================
    public void cadastrarCliente(CadastroClienteDto dto) throws SQLException, CampoJaCadastrado {

        if (emailExiste(dto.getEmail())) {
            throw new CampoJaCadastrado("E-mail já cadastrado!");
        }

        if (cpfExiste(dto.getCpf())) {
            throw new CampoJaCadastrado("CPF já cadastrado!");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setTelefone1(dto.getTelefone1());
        cliente.setConvenio(dto.getConvenio());
        cliente.setNumeroCarteirinha(dto.getNumeroCarteirinha());

        clienteDao.inserir(cliente);
    }

    // =========================
    // LISTAR CLIENTES
    // =========================
    public List<ListarClienteDto> listarClientes() throws SQLException {
        return clienteDao.listar().stream().map(c -> {
            ListarClienteDto dto = new ListarClienteDto();
            dto.setCodigo(c.getCodigo());
            dto.setNome(c.getNome());
            dto.setEmail(c.getEmail());
            dto.setCpf(c.getCpf());
            dto.setDataNascimento(c.getDataNascimento());
            dto.setTelefone1(c.getTelefone1());
            dto.setConvenio(c.getConvenio());
            dto.setNumeroCarteirinha(c.getNumeroCarteirinha());
            return dto;
        }).collect(Collectors.toList());
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Cliente buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        return clienteDao.buscarPorId(id);
    }

    // =========================
    // ATUALIZAR CLIENTE
    // =========================
    public void atualizarCliente(int id, AtualizarClienteDto dto) throws SQLException, EntidadeNaoEncontradaException {
        Cliente clienteExistente = clienteDao.buscarPorId(id);

        clienteExistente.setNome(dto.getNome());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setCpf(dto.getCpf());
        clienteExistente.setDataNascimento(dto.getDataNascimento());
        clienteExistente.setTelefone1(dto.getTelefone1());
        clienteExistente.setConvenio(dto.getConvenio());
        clienteExistente.setNumeroCarteirinha(dto.getNumeroCarteirinha());

        clienteDao.atualizar(clienteExistente);
    }

    // =========================
    // DELETAR CLIENTE
    // =========================
    public void deletarCliente(int id) throws SQLException, EntidadeNaoEncontradaException {
        clienteDao.deletar(id);
    }

    // =========================
    // VERIFICAÇÕES DE DUPLICIDADE
    // =========================
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
