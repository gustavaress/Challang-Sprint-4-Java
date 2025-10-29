package br.com.fiap.hc.dao;

import br.com.fiap.hc.model.Atendimento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AtendimentoDAO{

    @Inject
    DataSource dataSource;

    public List<Atendimento> listar() throws SQLException {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT ID_ATENDIMENTO, ID_CLIENTE, ID_ESPECIALISTA, DS_ATENDIMENTO, DT_ATENDIMENTO, ST_ATENDIMENTO FROM T_ATENDIMENTO";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId(rs.getLong("ID_ATENDIMENTO"));
                a.setDescricao(rs.getString("DS_ATENDIMENTO"));
                a.setStatus(rs.getString("ST_ATENDIMENTO"));
                a.setDataAtendimento(rs.getTimestamp("DT_ATENDIMENTO").toLocalDateTime());
                atendimentos.add(a);
            }
        }
        return atendimentos;
    }

    public void inserir(Atendimento atendimento) throws SQLException {
        String sql = "INSERT INTO T_ATENDIMENTO (ID_CLIENTE, ID_ESPECIALISTA, DS_ATENDIMENTO, DT_ATENDIMENTO, ST_ATENDIMENTO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, atendimento.getCliente().getId());
            ps.setLong(2, atendimento.getEspecialista().getId());
            ps.setString(3, atendimento.getDescricao());
            ps.setTimestamp(4, Timestamp.valueOf(atendimento.getDataAtendimento()));
            ps.setString(5, atendimento.getStatus());
            ps.executeUpdate();
        }
    }

    public void atualizarStatus(Long id, String status) throws SQLException {
        String sql = "UPDATE T_ATENDIMENTO SET ST_ATENDIMENTO=? WHERE ID_ATENDIMENTO=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM T_ATENDIMENTO WHERE ID_ATENDIMENTO=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
