package br.com.fiap.resource;

import br.com.fiap.dto.exame.CadastroExameDto;
import br.com.fiap.dto.exame.AtualizarExameDto;
import br.com.fiap.dto.exame.ListarExameDto;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.Exame;
import br.com.fiap.service.ExameService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/exames")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExameResource {

    @Inject
    ExameService exameService;

    @Inject
    ModelMapper mapper;

    // ✅ GET /exames — Lista todos os exames
    @GET
    public Response listarTodos() throws SQLException {
        List<Exame> lista = exameService.listarExames();
        List<ListarExameDto> dto = lista.stream()
                .map(e -> mapper.map(e, ListarExameDto.class))
                .collect(Collectors.toList());

        return Response.ok(dto).build();
    }

    // ✅ GET /exames/{id} — Busca exame por ID
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {
        Exame exame = exameService.buscarPorId(id);
        return Response.ok(mapper.map(exame, ListarExameDto.class)).build();
    }

    // ✅ POST /exames — Cria novo exame
    @POST
    public Response inserir(@Valid CadastroExameDto dto, @Context UriInfo uriInfo)
            throws SQLException, EntidadeNaoEncontradaException {

        Exame exame = mapper.map(dto, Exame.class);

        // 🔧 Corrige o NullPointer: cria o cliente e associa ao exame
        Cliente cliente = new Cliente();
        cliente.setCodigo(dto.getIdCliente());
        exame.setCliente(cliente);

        exameService.cadastrarExame(exame);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(exame.getCodigo()))
                .build();

        return Response.created(uri).entity(mapper.map(exame, ListarExameDto.class)).build();
    }

    // ✅ PUT /exames/{id} — Atualiza exame existente
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarExameDto dto)
            throws SQLException, EntidadeNaoEncontradaException {

        Exame exame = mapper.map(dto, Exame.class);
        exame.setCodigo(id);

        // Inclui também a referência ao cliente se houver no DTO
        if (dto.getIdCliente() > 0) {
            Cliente cliente = new Cliente();
            cliente.setCodigo(dto.getIdCliente());
            exame.setCliente(cliente);
        }

        exameService.atualizarExame(exame);

        return Response.ok(mapper.map(exame, ListarExameDto.class)).build();
    }

    // ✅ DELETE /exames/{id} — Deleta exame
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws SQLException, EntidadeNaoEncontradaException {

        exameService.deletarExame(id);
        return Response.noContent().build();
    }
}
