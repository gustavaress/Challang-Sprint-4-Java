package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Especialista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EspecialistaDao {

    @Inject
    DataSource dataSource;

    public void inserir(Especialista especialista) throws SQLException {
        String sql = """
                INSERT INTO T_ESPECIALISTA
                (ID_ESPECIALISTA, NM_ESPECIALISTA, EM_ESPECIALISTA, CPF_ESPECIALISTA, 
                 IDD_ESPECIALISTA, TEL1_ESPECIALISTA, CRM_ESPECIALISTA, ESP_ESPECIALISTA)
                VALUES (SEQ_ESPECIALISTA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_ESPECIALISTA"})) {

            ps.setString(1, especialista.getNome());
            ps.setString(2, especialista.getEmail());
            ps.setString(3, especialista.getCpf());
            ps.setInt(4, especialista.getIdade());
            ps.setString(5, especialista.getTelefone1());
            ps.setString(6, especialista.getCrm());
            ps.setString(7, especialista.getEspecialidade());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    especialista.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Especialista> listar() throws SQLException {
        String sql = "SELECT * FROM T_ESPECIALISTA";
        List<Especialista> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(parseEspecialista(rs));
            }
        }
        return lista;
    }

    public Especialista buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_ESPECIALISTA WHERE ID_ESPECIALISTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Especialista não encontrado!");
                }
                return parseEspecialista(rs);
            }
        }
    }

    public Especialista buscarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_ESPECIALISTA WHERE EM_ESPECIALISTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("E-mail não encontrado!");
                }
                return parseEspecialista(rs);
            }
        }
    }

    public Especialista buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_ESPECIALISTA WHERE CPF_ESPECIALISTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("CPF não encontrado!");
                }
                return parseEspecialista(rs);
            }
        }
    }

    public Especialista buscarPorCrm(String crm) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_ESPECIALISTA WHERE CRM_ESPECIALISTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, crm);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("CRM não encontrado!");
                }
                return parseEspecialista(rs);
            }
        }
    }

    public void atualizar(Especialista especialista) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_ESPECIALISTA
                SET NM_ESPECIALISTA = ?, EM_ESPECIALISTA = ?, CPF_ESPECIALISTA = ?, 
                    IDD_ESPECIALISTA = ?, TEL1_ESPECIALISTA = ?, CRM_ESPECIALISTA = ?, 
                    ESP_ESPECIALISTA = ?
                WHERE ID_ESPECIALISTA = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialista.getNome());
            ps.setString(2, especialista.getEmail());
            ps.setString(3, especialista.getCpf());
            ps.setInt(4, especialista.getIdade());
            ps.setString(5, especialista.getTelefone1());
            ps.setString(6, especialista.getCrm());
            ps.setString(7, especialista.getEspecialidade());
            ps.setInt(8, especialista.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Especialista não encontrado para atualizar!");
            }
        }
    }

    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_ESPECIALISTA WHERE ID_ESPECIALISTA = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Especialista não encontrado para remover!");
            }
        }
    }

    private Especialista parseEspecialista(ResultSet rs) throws SQLException {
        return new Especialista(
                rs.getInt("ID_ESPECIALISTA"),
                rs.getString("NM_ESPECIALISTA"),
                rs.getString("EM_ESPECIALISTA"),
                rs.getString("CPF_ESPECIALISTA"),
                rs.getInt("IDD_ESPECIALISTA"),
                rs.getString("TEL1_ESPECIALISTA"),
                rs.getString("CRM_ESPECIALISTA"),
                rs.getString("ESP_ESPECIALISTA")
        );
    }
}
