package br.com.fiap.hc.dao;

import br.com.fiap.hc.model.Especialista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EspecialistaDAO {

    @Inject
    DataSource dataSource;

    public List<Especialista> listar() throws SQLException {
        List<Especialista> especialistas = new ArrayList<>();
        String sql = "SELECT ID_ESPECIALISTA, NM_ESPECIALISTA, DS_ESPECIALIDADE, NR_CRM FROM T_ESPECIALISTA";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Especialista e = new Especialista();
                e.setId(rs.getLong("ID_ESPECIALISTA"));
                e.setNome(rs.getString("NM_ESPECIALISTA"));
                e.setEspecialidade(rs.getString("DS_ESPECIALIDADE"));
                e.setCrm(rs.getString("NR_CRM"));
                especialistas.add(e);
            }
        }
        return especialistas;
    }

    public void inserir(Especialista especialista) throws SQLException {
        String sql = "INSERT INTO T_ESPECIALISTA (NM_ESPECIALISTA, DS_ESPECIALIDADE, NR_CRM) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, especialista.getNome());
            ps.setString(2, especialista.getEspecialidade());
            ps.setString(3, especialista.getCrm());
            ps.executeUpdate();
        }
    }

    public void atualizar(Long id, Especialista especialista) throws SQLException {
        String sql = "UPDATE T_ESPECIALISTA SET NM_ESPECIALISTA=?, DS_ESPECIALIDADE=?, NR_CRM=? WHERE ID_ESPECIALISTA=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, especialista.getNome());
            ps.setString(2, especialista.getEspecialidade());
            ps.setString(3, especialista.getCrm());
            ps.setLong(4, id);
            ps.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM T_ESPECIALISTA WHERE ID_ESPECIALISTA=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
