package br.com.fiap.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class HomeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("Hello World!", "API HC Acesso Fácil está em execução com sucesso!");
        response.put("endpoints", new String[]{"/clientes", "/especialistas", "/atendimentos"});
        return response;
    }
}
