package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteDao {

    @Inject
    DataSource dataSource;

    public void inserir(Cliente cliente) throws SQLException {
        String sql = """
                INSERT INTO T_CLIENTE
                (ID_CLIENTE, NM_CLIENTE, EM_CLIENTE, CPF_CLIENTE, IDD_CLIENTE, TEL1_CLIENTE)
                VALUES (SEQ_CLIENTE.NEXTVAL, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_CLIENTE"})) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setInt(4, cliente.getIdade());
            ps.setString(5, cliente.getTelefone1());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT * FROM T_CLIENTE";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(parseCliente(rs));
            }
        }
        return clientes;
    }

    public Cliente buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Cliente não encontrado!");
                }
                return parseCliente(rs);
            }
        }
    }

    public Cliente buscarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_CLIENTE WHERE EM_CLIENTE = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("E-mail não encontrado!");
                }
                return parseCliente(rs);
            }
        }
    }

    public Cliente buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_CLIENTE WHERE CPF_CLIENTE = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("CPF não encontrado!");
                }
                return parseCliente(rs);
            }
        }
    }

    public void atualizar(Cliente cliente) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_CLIENTE
                SET NM_CLIENTE = ?, EM_CLIENTE = ?, CPF_CLIENTE = ?, IDD_CLIENTE = ?, TEL1_CLIENTE = ?
                WHERE ID_CLIENTE = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setInt(4, cliente.getIdade());
            ps.setString(5, cliente.getTelefone1());
            ps.setInt(6, cliente.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Cliente não encontrado para atualizar!");
            }
        }
    }

    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Cliente não encontrado para remover!");
            }
        }
    }

    private Cliente parseCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("ID_CLIENTE"),
                rs.getString("NM_CLIENTE"),
                rs.getString("EM_CLIENTE"),
                rs.getString("CPF_CLIENTE"),
                rs.getInt("IDD_CLIENTE"),
                rs.getString("TEL1_CLIENTE")
        );
    }
}
