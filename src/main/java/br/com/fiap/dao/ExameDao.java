package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.AtendimentoStatus;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.Exame;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ExameDao {

    @Inject
    DataSource dataSource;

    // ✅ INSERIR
    public void inserir(Exame exame) throws SQLException {
        String sql = """
                INSERT INTO T_EXAME
                (ID_EXAME, ID_CLIENTE, DS_EXAME, DT_ATENDIMENTO, ST_ATENDIMENTO)
                VALUES (SEQ_EXAME.NEXTVAL, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_EXAME"})) {

            ps.setInt(1, exame.getCliente().getCodigo());
            ps.setString(2, exame.getDescricao());
            ps.setTimestamp(3, Timestamp.valueOf(exame.getDataAtendimento()));
            ps.setString(4, exame.getStatus().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    exame.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    // ✅ LISTAR TODOS
    public List<Exame> listar() throws SQLException {
        String sql = """
                SELECT E.*, C.NM_CLIENTE, C.EM_CLIENTE
                FROM T_EXAME E
                JOIN T_CLIENTE C ON E.ID_CLIENTE = C.ID_CLIENTE
                ORDER BY E.DT_ATENDIMENTO DESC
                """;

        List<Exame> exames = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                exames.add(parseExame(rs));
            }
        }

        return exames;
    }

    // ✅ BUSCAR POR ID
    public Exame buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                SELECT E.*, C.NM_CLIENTE, C.EM_CLIENTE
                FROM T_EXAME E
                JOIN T_CLIENTE C ON E.ID_CLIENTE = C.ID_CLIENTE
                WHERE E.ID_EXAME = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Exame não encontrado!");
                }
                return parseExame(rs);
            }
        }
    }

    // ✅ ATUALIZAR
    public void atualizar(Exame exame) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_EXAME
                SET ID_CLIENTE = ?, DS_EXAME = ?, DT_ATENDIMENTO = ?, ST_ATENDIMENTO = ?
                WHERE ID_EXAME = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, exame.getCliente().getCodigo());
            ps.setString(2, exame.getDescricao());
            ps.setTimestamp(3, Timestamp.valueOf(exame.getDataAtendimento()));
            ps.setString(4, exame.getStatus().name());
            ps.setInt(5, exame.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Exame não encontrado para atualizar!");
            }
        }
    }

    // ✅ DELETAR
    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_EXAME WHERE ID_EXAME = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Exame não encontrado para remover!");
            }
        }
    }

    // ✅ MÉTODO AUXILIAR PARA MONTAR OBJETO EXAME
    private Exame parseExame(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigo(rs.getInt("ID_CLIENTE"));
        cliente.setNome(rs.getString("NM_CLIENTE"));
        cliente.setEmail(rs.getString("EM_CLIENTE"));

        return new Exame(
                rs.getInt("ID_EXAME"),
                cliente,
                rs.getString("DS_EXAME"),
                rs.getTimestamp("DT_ATENDIMENTO").toLocalDateTime(),
                AtendimentoStatus.valueOf(rs.getString("ST_ATENDIMENTO"))
        );
    }
}
