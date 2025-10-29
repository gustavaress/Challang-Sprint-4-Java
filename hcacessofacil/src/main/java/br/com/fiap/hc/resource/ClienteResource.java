package br.com.fiap.hc.resource;

import br.com.fiap.hc.dto.ClienteDTO;
import br.com.fiap.hc.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @GET
    public Response listar() throws SQLException {
        List<ClienteDTO> clientes = service.listar();
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws SQLException {
        ClienteDTO cliente = service.buscarPorId(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
        return Response.ok(cliente).build();
    }

    @POST
    public Response cadastrar(@Valid ClienteDTO dto) throws SQLException {
        service.cadastrar(dto);
        return Response.status(Response.Status.CREATED).entity("Cliente cadastrado com sucesso!").build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteDTO dto) throws SQLException {
        service.atualizar(id, dto);
        return Response.ok("Cliente atualizado com sucesso!").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) throws SQLException {
        service.deletar(id);
        return Response.ok("Cliente removido com sucesso!").build();
    }
}
