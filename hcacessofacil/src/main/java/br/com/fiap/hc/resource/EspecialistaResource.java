package br.com.fiap.hc.resource;

import br.com.fiap.hc.dto.EspecialistaDTO;
import br.com.fiap.hc.service.EspecialistaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialistaResource {

    @Inject
    EspecialistaService service;

    @GET
    public Response listar() throws SQLException {
        List<EspecialistaDTO> lista = service.listar();
        return Response.ok(lista).build();
    }

    @POST
    public Response cadastrar(@Valid EspecialistaDTO dto) throws SQLException {
        service.cadastrar(dto);
        return Response.status(Response.Status.CREATED).entity("Especialista cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid EspecialistaDTO dto) throws SQLException {
        service.atualizar(id, dto);
        return Response.ok("Especialista atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) throws SQLException {
        service.deletar(id);
        return Response.ok("Especialista removido com sucesso!").build();
    }
}
