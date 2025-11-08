package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteDao {

    @Inject
    DataSource dataSource;

    // =========================
    // INSERIR CLIENTE
    // =========================
    public void inserir(Cliente cliente) throws SQLException {
        String sql = """
                INSERT INTO T_CLIENTE
                (ID_CLIENTE, NM_CLIENTE, EM_CLIENTE, CPF_CLIENTE, DTNASC_CLIENTE, 
                 TEL1_CLIENTE, CONV_CLIENTE, NUM_CARTERINHA)
                VALUES (SEQ_CLIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_CLIENTE"})) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setDate(4, Date.valueOf(cliente.getDataNascimento()));
            ps.setString(5, cliente.getTelefone1());
            ps.setString(6, cliente.getConvenio());
            ps.setString(7, cliente.getNumeroCarteirinha());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    // =========================
    // LISTAR TODOS OS CLIENTES
    // =========================
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

    // =========================
    // BUSCAR POR ID
    // =========================
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

    // =========================
    // BUSCAR POR EMAIL
    // =========================
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

    // =========================
    // BUSCAR POR CPF
    // =========================
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

    // =========================
    // ATUALIZAR CLIENTE
    // =========================
    public void atualizar(Cliente cliente) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_CLIENTE
                SET NM_CLIENTE = ?, EM_CLIENTE = ?, CPF_CLIENTE = ?, DTNASC_CLIENTE = ?, 
                    TEL1_CLIENTE = ?, CONV_CLIENTE = ?, NUM_CARTERINHA = ?
                WHERE ID_CLIENTE = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setDate(4, Date.valueOf(cliente.getDataNascimento()));
            ps.setString(5, cliente.getTelefone1());
            ps.setString(6, cliente.getConvenio());
            ps.setString(7, cliente.getNumeroCarteirinha());
            ps.setInt(8, cliente.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Cliente não encontrado para atualizar!");
            }
        }
    }

    // =========================
    // DELETAR CLIENTE
    // =========================
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

    // =========================
    // CONVERSOR RESULTSET → CLIENTE
    // =========================
    private Cliente parseCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigo(rs.getInt("ID_CLIENTE"));
        cliente.setNome(rs.getString("NM_CLIENTE"));
        cliente.setEmail(rs.getString("EM_CLIENTE"));
        cliente.setCpf(rs.getString("CPF_CLIENTE"));

        Date data = rs.getDate("DTNASC_CLIENTE");
        if (data != null) {
            cliente.setDataNascimento(data.toLocalDate());
        }

        cliente.setTelefone1(rs.getString("TEL1_CLIENTE"));
        cliente.setConvenio(rs.getString("CONV_CLIENTE"));
        cliente.setNumeroCarteirinha(rs.getString("NUM_CARTERINHA"));

        return cliente;
    }
}
