package br.com.fiap.hc.service;

import br.com.fiap.hc.dao.EspecialistaDAO;
import br.com.fiap.hc.dto.EspecialistaDTO;
import br.com.fiap.hc.model.Especialista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EspecialistaService {

    @Inject
    EspecialistaDAO especialistaDAO;

    @Inject
    ModelMapper mapper;

    public List<EspecialistaDTO> listar() throws SQLException {
        return especialistaDAO.listar()
                .stream()
                .map(e -> mapper.map(e, EspecialistaDTO.class))
                .collect(Collectors.toList());
    }

    public void cadastrar(EspecialistaDTO dto) throws SQLException {
        Especialista especialista = mapper.map(dto, Especialista.class);
        especialistaDAO.inserir(especialista);
    }

    public void atualizar(Long id, EspecialistaDTO dto) throws SQLException {
        Especialista especialista = mapper.map(dto, Especialista.class);
        especialistaDAO.atualizar(id, especialista);
    }

    public void deletar(Long id) throws SQLException {
        especialistaDAO.deletar(id);
    }
}
