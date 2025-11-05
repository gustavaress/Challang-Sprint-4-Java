package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Atendimento;
import br.com.fiap.model.AtendimentoStatus;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.Especialista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AtendimentoDao {

    @Inject
    DataSource dataSource;

    public void inserir(Atendimento atendimento) throws SQLException {
        String sql = """
            INSERT INTO T_ATENDIMENTO
            (ID_ATENDIMENTO, ID_CLIENTE, ID_ESPECIALISTA, DS_ATENDIMENTO, DT_ATENDIMENTO, ST_ATENDIMENTO)
            VALUES (SEQ_ATENDIMENTO.NEXTVAL, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_ATENDIMENTO"})) {

            ps.setInt(1, atendimento.getCliente().getCodigo());
            ps.setInt(2, atendimento.getEspecialista().getCodigo());
            ps.setString(3, atendimento.getDescricao());
            ps.setTimestamp(4, Timestamp.valueOf(atendimento.getDataAtendimento()));
            ps.setString(5, atendimento.getStatus().getValorBanco());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    atendimento.setCodigo(rs.getInt(1));
                }
            }
        }
    }

    public List<Atendimento> listar() throws SQLException {
        String sql = "SELECT * FROM T_ATENDIMENTO";
        List<Atendimento> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(parseAtendimento(rs));
            }
        }
        return lista;
    }

    public Atendimento buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_ATENDIMENTO WHERE ID_ATENDIMENTO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Atendimento não encontrado!");
                }
                return parseAtendimento(rs);
            }
        }
    }

    public void atualizar(Atendimento atendimento) throws SQLException, EntidadeNaoEncontradaException {
        String sql = """
                UPDATE T_ATENDIMENTO
                SET ID_CLIENTE = ?, ID_ESPECIALISTA = ?, DS_ATENDIMENTO = ?, DT_ATENDIMENTO = ?, ST_ATENDIMENTO = ?
                WHERE ID_ATENDIMENTO = ?
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, atendimento.getCliente().getCodigo());
            ps.setInt(2, atendimento.getEspecialista().getCodigo());
            ps.setString(3, atendimento.getDescricao());
            ps.setTimestamp(4, Timestamp.valueOf(atendimento.getDataAtendimento()));
            ps.setString(5, atendimento.getStatus().getValorBanco());
            ps.setInt(6, atendimento.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Atendimento não encontrado para atualizar!");
            }
        }
    }

    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_ATENDIMENTO WHERE ID_ATENDIMENTO = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Atendimento não encontrado para remover!");
            }
        }
    }

    private Atendimento parseAtendimento(ResultSet rs) throws SQLException {
        Atendimento atendimento = new Atendimento();

        atendimento.setCodigo(rs.getInt("ID_ATENDIMENTO"));
        atendimento.setDescricao(rs.getString("DS_ATENDIMENTO"));
        atendimento.setDataAtendimento(rs.getTimestamp("DT_ATENDIMENTO").toLocalDateTime());
        atendimento.setStatus(AtendimentoStatus.fromDbValue(rs.getString("ST_ATENDIMENTO")));

        Cliente cliente = new Cliente();
        cliente.setCodigo(rs.getInt("ID_CLIENTE"));
        atendimento.setCliente(cliente);

        Especialista especialista = new Especialista();
        especialista.setCodigo(rs.getInt("ID_ESPECIALISTA"));
        atendimento.setEspecialista(especialista);

        return atendimento;
    }
}
