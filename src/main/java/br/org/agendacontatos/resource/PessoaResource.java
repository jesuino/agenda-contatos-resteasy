package br.org.agendacontatos.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.org.agendacontatos.model.Pessoa;


@Path("/pessoa")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface PessoaResource {

	@GET
	@Wrapped(element = "pessoas")
	public List<Pessoa> recuperarTodos();
	@GET
	@Path("/{id}")
	public Response recuperar(@PathParam("id") int id);

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response atualizar(@PathParam("id") int id, Pessoa novaPessoa);

	@DELETE
	@Path("/{id}")
	public Response apagar(@PathParam("id") int id);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response criar(Pessoa p, @Context UriInfo info);

}
