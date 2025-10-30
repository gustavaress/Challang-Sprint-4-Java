package br.com.fiap.resource;

import br.com.fiap.dto.atendimento.CadastroAtendimentoDto;
import br.com.fiap.dto.atendimento.ListarAtendimentoDto;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Atendimento;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.Especialista;
import br.com.fiap.service.AtendimentoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/atendimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    @Inject
    private AtendimentoService atendimentoService;

    @Inject
    private ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Atendimento> atendimentos = atendimentoService.listarAtendimentos();

        List<ListarAtendimentoDto> dtoList = atendimentos.stream()
                .map(a -> mapper.map(a, ListarAtendimentoDto.class))
                .collect(Collectors.toList());

        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id)
            throws EntidadeNaoEncontradaException, SQLException {

        Atendimento atendimento = atendimentoService.buscarPorId(id);
        ListarAtendimentoDto dto = mapper.map(atendimento, ListarAtendimentoDto.class);

        return Response.ok(dto).build();
    }

    @POST
    public Response inserir(@Valid CadastroAtendimentoDto dto, @Context UriInfo uriInfo)
            throws Exception {

        Atendimento atendimento = mapper.map(dto, Atendimento.class);

        atendimento.setCliente(new Cliente());
        atendimento.getCliente().setCodigo(dto.getClienteId());

        atendimento.setEspecialista(new Especialista());
        atendimento.getEspecialista().setCodigo(dto.getEspecialistaId());

        atendimentoService.cadastrarAtendimento(atendimento);

        ListarAtendimentoDto responseDto = mapper.map(atendimento, ListarAtendimentoDto.class);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(atendimento.getCodigo()))
                .build();

        return Response.created(uri).entity(responseDto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroAtendimentoDto dto)
            throws Exception {

        Atendimento atendimento = mapper.map(dto, Atendimento.class);
        atendimento.setCodigo(id);

        atendimento.setCliente(new Cliente());
        atendimento.getCliente().setCodigo(dto.getClienteId());

        atendimento.setEspecialista(new Especialista());
        atendimento.getEspecialista().setCodigo(dto.getEspecialistaId());

        atendimentoService.atualizarAtendimento(atendimento);

        ListarAtendimentoDto responseDto = mapper.map(atendimento, ListarAtendimentoDto.class);

        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws EntidadeNaoEncontradaException, SQLException {

        atendimentoService.deletarAtendimento(id);

        return Response.noContent().build();
    }
}
