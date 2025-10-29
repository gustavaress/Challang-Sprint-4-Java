package br.com.fiap.hc.service;

import br.com.fiap.hc.dao.AtendimentoDAO;
import br.com.fiap.hc.dto.AtendimentoDTO;
import br.com.fiap.hc.model.Atendimento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AtendimentoService {

    @Inject
    AtendimentoDAO atendimentoDAO;

    @Inject
    ModelMapper mapper;

    public List<AtendimentoDTO> listar() throws SQLException {
        return atendimentoDAO.listar()
                .stream()
                .map(a -> mapper.map(a, AtendimentoDTO.class))
                .collect(Collectors.toList());
    }

    public void cadastrar(AtendimentoDTO dto) throws SQLException {
        Atendimento atendimento = mapper.map(dto, Atendimento.class);
        atendimentoDAO.inserir(atendimento);
    }

    public void atualizarStatus(Long id, String status) throws SQLException {
        atendimentoDAO.atualizarStatus(id, status);
    }

    public void deletar(Long id) throws SQLException {
        atendimentoDAO.deletar(id);
    }
}
