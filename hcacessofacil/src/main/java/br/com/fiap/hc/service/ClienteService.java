package br.com.fiap.hc.service;

import br.com.fiap.hc.dao.ClienteDAO;
import br.com.fiap.hc.dto.ClienteDTO;
import br.com.fiap.hc.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteDAO clienteDAO;

    @Inject
    ModelMapper mapper;

    public List<ClienteDTO> listar() throws SQLException {
        return clienteDAO.listar()
                .stream()
                .map(cliente -> mapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) throws SQLException {
        Cliente cliente = clienteDAO.buscarPorId(id);
        return cliente != null ? mapper.map(cliente, ClienteDTO.class) : null;
    }

    public void cadastrar(ClienteDTO dto) throws SQLException {
        Cliente cliente = mapper.map(dto, Cliente.class);
        clienteDAO.inserir(cliente);
    }

    public void atualizar(Long id, ClienteDTO dto) throws SQLException {
        Cliente cliente = mapper.map(dto, Cliente.class);
        clienteDAO.atualizar(id, cliente);
    }

    public void deletar(Long id) throws SQLException {
        clienteDAO.deletar(id);
    }
}
