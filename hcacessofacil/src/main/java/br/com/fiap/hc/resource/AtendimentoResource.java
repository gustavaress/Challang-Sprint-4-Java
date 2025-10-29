package br.com.fiap.hc.resource;

import br.com.fiap.hc.dto.AtendimentoDTO;
import br.com.fiap.hc.service.AtendimentoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/atendimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    @Inject
    AtendimentoService service;

    @GET
    public Response listar() throws SQLException {
        List<AtendimentoDTO> lista = service.listar();
        return Response.ok(lista).build();
    }

    @POST
    public Response cadastrar(@Valid AtendimentoDTO dto) throws SQLException {
        service.cadastrar(dto);
        return Response.status(Response.Status.CREATED).entity("Atendimento cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/{id}/status")
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("novoStatus") String status) throws SQLException {
        service.atualizarStatus(id, status);
        return Response.ok("Status do atendimento atualizado para: " + status).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) throws SQLException {
        service.deletar(id);
        return Response.ok("Atendimento removido com sucesso!").build();
    }
}
