package br.com.fiap.resource;

import br.com.fiap.dto.especialista.CadastroEspecialistaDto;
import br.com.fiap.dto.especialista.AtualizarEspecialistaDto;
import br.com.fiap.dto.especialista.ListarEspecialistaDto;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Especialista;
import br.com.fiap.service.EspecialistaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialistaResource {

    @Inject
    EspecialistaService especialistaService;

    @Inject
    ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Especialista> lista = especialistaService.listarEspecialistas();
        List<ListarEspecialistaDto> dto = lista.stream()
                .map(e -> mapper.map(e, ListarEspecialistaDto.class))
                .collect(Collectors.toList());
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {
        Especialista esp = especialistaService.buscarPorId(id);
        return Response.ok(mapper.map(esp, ListarEspecialistaDto.class)).build();
    }

    @POST
    public Response inserir(@Valid CadastroEspecialistaDto dto, @Context UriInfo uriInfo)
            throws SQLException, CampoJaCadastrado {
        Especialista esp = mapper.map(dto, Especialista.class);
        especialistaService.cadastrarEspecialista(esp);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(esp.getCodigo()))
                .build();

        return Response.created(uri).entity(mapper.map(esp, ListarEspecialistaDto.class)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarEspecialistaDto dto)
            throws SQLException, EntidadeNaoEncontradaException {
        Especialista esp = mapper.map(dto, Especialista.class);
        esp.setCodigo(id);
        especialistaService.atualizarEspecialista(esp);

        return Response.ok(mapper.map(esp, ListarEspecialistaDto.class)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {
        especialistaService.deletarEspecialista(id);
        return Response.noContent().build();
    }
}
