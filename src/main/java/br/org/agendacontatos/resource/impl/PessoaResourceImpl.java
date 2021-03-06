package br.org.agendacontatos.resource.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.org.agendacontatos.model.Pessoa;
import br.org.agendacontatos.persistencia.GerenciaEntityManager;
import br.org.agendacontatos.resource.PessoaResource;

public class PessoaResourceImpl implements PessoaResource {
	EntityManager em;

	public PessoaResourceImpl() {
		em = GerenciaEntityManager.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> recuperarTodos() {
		List<Pessoa> pessoas = em.createQuery("from Pessoa").getResultList();
		//Retornar nulo resulta em 204
		if(pessoas.size() == 0)
			return null;
		return em.createQuery("from Pessoa").getResultList();
	}

	public Response recuperar(@PathParam("id") int id) {
		Pessoa p = em.find(Pessoa.class, id);
		if (p == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(p).build();
	}

	public Response atualizar(@PathParam("id") int id, Pessoa novaPessoa) {
		Pessoa p = em.find(Pessoa.class, id);
		if (p == null)
			return Response.status(Response.Status.NOT_FOUND).build();
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

	public Response apagar(@PathParam("id") int id) {
		Pessoa p = em.find(Pessoa.class, id);
		if (p == null)	
			return Response.status(Response.Status.NOT_FOUND).build();		
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();		
		return Response.noContent().build();
	}

	public Response criar(Pessoa p, @Context UriInfo info) {
		if (p == null)
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		em.getTransaction().begin();
		em.persist(p);
		em.flush();
		em.getTransaction().commit();
		UriBuilder builder = info.getAbsolutePathBuilder();		
		builder.path(String.valueOf(p.getId()));
		return Response.created(builder.build()).build();
	}

}
