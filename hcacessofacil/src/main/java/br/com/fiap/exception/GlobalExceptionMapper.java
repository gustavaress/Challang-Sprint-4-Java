package br.com.fiap.exception;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace(); // opcional: remove em produção

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErroResposta("Erro interno no servidor."))
                .build();
    }
}
