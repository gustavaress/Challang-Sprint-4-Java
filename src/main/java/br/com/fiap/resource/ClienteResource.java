package br.com.fiap.resource;

import br.com.fiap.dto.cliente.CadastroClienteDto;
import br.com.fiap.dto.cliente.AtualizarClienteDto;
import br.com.fiap.dto.cliente.ListarClienteDto;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public Response listarTodos() throws SQLException {
        List<ListarClienteDto> lista = clienteService.listarClientes();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {

        ListarClienteDto dto = new ListarClienteDto();
        var cliente = clienteService.buscarPorId(id);

        dto.setCodigo(cliente.getCodigo());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setTelefone1(cliente.getTelefone1());
        dto.setConvenio(cliente.getConvenio());
        dto.setNumeroCarteirinha(cliente.getNumeroCarteirinha());

        return Response.ok(dto).build();
    }

    @POST
    public Response inserir(@Valid CadastroClienteDto dto, @Context UriInfo uriInfo)
            throws SQLException, CampoJaCadastrado {

        clienteService.cadastrarCliente(dto);

        // Recupera o novo cliente (opcional, se quiser devolver o objeto completo)
        ListarClienteDto clienteCriado = new ListarClienteDto();
        clienteCriado.setNome(dto.getNome());
        clienteCriado.setEmail(dto.getEmail());
        clienteCriado.setCpf(dto.getCpf());
        clienteCriado.setDataNascimento(dto.getDataNascimento());
        clienteCriado.setTelefone1(dto.getTelefone1());
        clienteCriado.setConvenio(dto.getConvenio());
        clienteCriado.setNumeroCarteirinha(dto.getNumeroCarteirinha());

        URI uri = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(uri).entity(clienteCriado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarClienteDto dto)
            throws SQLException, EntidadeNaoEncontradaException {

        clienteService.atualizarCliente(id, dto);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {

        clienteService.deletarCliente(id);
        return Response.noContent().build();
    }
}
