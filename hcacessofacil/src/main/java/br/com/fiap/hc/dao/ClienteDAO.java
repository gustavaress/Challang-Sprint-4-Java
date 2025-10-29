package br.com.fiap.hc.dao;

import br.com.fiap.hc.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteDAO {

    @Inject
    DataSource dataSource;

    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, NM_CLIENTE, DS_EMAIL, NR_TELEFONE FROM T_CLIENTE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getLong("ID_CLIENTE"));
                c.setNome(rs.getString("NM_CLIENTE"));
                c.setEmail(rs.getString("DS_EMAIL"));
                c.setTelefone(rs.getString("NR_TELEFONE"));
                clientes.add(c);
            }
        }
        return clientes;
    }

    public Cliente buscarPorId(Long id) throws SQLException {
        String sql = "SELECT ID_CLIENTE, NM_CLIENTE, DS_EMAIL, NR_TELEFONE FROM T_CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("NM_CLIENTE"),
                        rs.getString("DS_EMAIL"),
                        rs.getString("NR_TELEFONE")
                );
            }
            return null;
        }
    }

    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO T_CLIENTE (NM_CLIENTE, DS_EMAIL, NR_TELEFONE) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefone());
            ps.executeUpdate();
        }
    }

    public void atualizar(Long id, Cliente cliente) throws SQLException {
        String sql = "UPDATE T_CLIENTE SET NM_CLIENTE=?, DS_EMAIL=?, NR_TELEFONE=? WHERE ID_CLIENTE=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefone());
            ps.setLong(4, id);
            ps.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM T_CLIENTE WHERE ID_CLIENTE=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
