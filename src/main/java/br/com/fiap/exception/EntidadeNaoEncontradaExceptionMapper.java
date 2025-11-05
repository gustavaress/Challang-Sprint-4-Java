package br.com.fiap.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntidadeNaoEncontradaExceptionMapper implements ExceptionMapper<EntidadeNaoEncontradaException> {

    @Override
    public Response toResponse(EntidadeNaoEncontradaException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErroResposta(e.getMessage()))
                .build();
    }
}
