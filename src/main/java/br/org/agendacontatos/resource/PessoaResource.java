package br.org.agendacontatos.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.org.agendacontatos.model.Pessoa;

/**
 * TODO: Precisam ser testados *todos* os métodos
 *
 */
@Path("/pessoa")
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
public class PessoaResource {
	EntityManager em;

	@GET
	@Wrapped(element = "pessoas")
	public List<Pessoa> recuperarTodos() {
		return em.createQuery("from Pessoa").getResultList();
	}

	@GET
	@Path("/{id}")
	public Response recuperar(@PathParam("id") int id) {
		Pessoa p = em.find(Pessoa.class, id);
		return Response.ok(p).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
	public Response atualizar(@PathParam("id") int id, Pessoa novaPessoa) {
		Pessoa p = em.find(Pessoa.class, id);
		if (p == null)
			return null;
		p.setDataCriacao(novaPessoa.getDataCriacao());
		p.setEmail(novaPessoa.getEmail());
		p.setEndereco(novaPessoa.getEndereco());
		p.setNome(novaPessoa.getNome());
		p.setTelefone(novaPessoa.getTelefone());
		p.setUrl(novaPessoa.getUrl());
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		return Response.ok(p).build();
	}

	@DELETE
	@Path("/{id}")
	public Response apagar(@PathParam("id") int id) {
		Pessoa p = em.find(Pessoa.class, id);
		if (p == null)
			return null;
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
		return Response.ok().build();
	}

	@POST
	@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
	public Response criar(Pessoa p, @Context UriInfo info) {
		if (p == null)
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		UriBuilder builder = info.getBaseUriBuilder();
		builder.path(String.valueOf(p.getId()));
		return Response.created(builder.build()).build();
	}

}
