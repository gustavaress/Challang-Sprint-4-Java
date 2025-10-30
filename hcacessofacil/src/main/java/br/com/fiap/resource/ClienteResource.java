package br.com.fiap.resource;

import br.com.fiap.dto.cliente.CadastroClienteDto;
import br.com.fiap.dto.cliente.AtualizarClienteDto;
import br.com.fiap.dto.cliente.ListarClienteDto;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import br.com.fiap.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Cliente> lista = clienteService.listarClientes();
        List<ListarClienteDto> dto = lista.stream()
                .map(p -> mapper.map(p, ListarClienteDto.class))
                .collect(Collectors.toList());

        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {

        Cliente cliente = clienteService.buscarPorId(id);
        return Response.ok(mapper.map(cliente, ListarClienteDto.class)).build();
    }

    @POST
    public Response inserir(@Valid CadastroClienteDto dto, @Context UriInfo uriInfo)
            throws SQLException, CampoJaCadastrado {

        Cliente cliente = mapper.map(dto, Cliente.class);
        clienteService.cadastrarCliente(cliente);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(cliente.getCodigo()))
                .build();

        return Response.created(uri).entity(mapper.map(cliente, ListarClienteDto.class)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarClienteDto dto)
            throws SQLException, EntidadeNaoEncontradaException {

        Cliente cliente = mapper.map(dto, Cliente.class);
        cliente.setCodigo(id);

        clienteService.atualizarCliente(cliente);

        return Response.ok(mapper.map(cliente, ListarClienteDto.class)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {

        clienteService.deletarCliente(id);
        return Response.noContent().build();
    }
}
