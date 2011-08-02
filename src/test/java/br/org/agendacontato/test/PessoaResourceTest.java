package br.org.agendacontato.test;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.agendacontatos.model.Pessoa;
import br.org.agendacontatos.resource.PessoaResource;
import br.org.agendacontatos.resource.impl.PessoaResourceImpl;

public class PessoaResourceTest {
	final String BASE_URL = "http://localhost:8081";	

	/**
	 * Sobe o servidor interno para testes
	 */
	@BeforeClass
	public static void iniciaServidor() {		
		TJWSEmbeddedJaxrsServer tjws = new TJWSEmbeddedJaxrsServer();		
		tjws.setPort(8081);
		tjws.getDeployment().getActualResourceClasses()
				.add(PessoaResourceImpl.class);
		tjws.start();
	}

	@Test
	public void testaTudo() throws Exception {		
		String novaPessoaURI;				
		Pessoa p = new Pessoa("Maria", "555-123456", "maria@provedor.com",
				"www.cantinhodamaria.com", "lugar nenhum", new Date());				

		//Tenta criar
		ClientRequest cr1 = new ClientRequest(BASE_URL + "/pessoa");
		cr1.body(MediaType.APPLICATION_XML, p);	
		Response r = cr1.post(Pessoa.class);		
		Assert.assertEquals(201, r.getStatus());	
		novaPessoaURI = (String) r.getMetadata().get("location").get(0);
		
		//verifica se temos essa pessoa na lista usando o cliente proxy
		PessoaResource pr = ProxyFactory.create(PessoaResource.class, BASE_URL);
		List<Pessoa> pessoas = pr.recuperarTodos();	
		Assert.assertEquals(1, pessoas.size());
		Assert.assertEquals(p.getNome(), pessoas.get(0).getNome());
		
		//verifica se foi criado
		ClientRequest cr2 = new ClientRequest(novaPessoaURI);
		cr2.accept(MediaType.APPLICATION_XML);
		r = cr2.get(Pessoa.class);
		Assert.assertEquals(200, r.getStatus());
		Assert.assertEquals(p.getNome(), ((Pessoa) r.getEntity()).getNome());
				
		//tenta modificar
		ClientRequest cr3 = new ClientRequest(novaPessoaURI);
		p.setNome("NOVO NOME");
		cr3.body(MediaType.APPLICATION_XML, p);		
		cr3.accept(MediaType.APPLICATION_XML);
		r = cr3.put(Pessoa.class);
		Assert.assertEquals(200, r.getStatus());
		Assert.assertEquals(p.getNome(), ((Pessoa) r.getEntity()).getNome());		
				
		//tenta apagar
		ClientRequest cr4 = new ClientRequest(novaPessoaURI);
		r = cr4.delete();
		Assert.assertEquals(204, r.getStatus());
		
		//verifica se não existe mais
		ClientRequest cr5 = new ClientRequest(novaPessoaURI);
		r = cr5.get();
		Assert.assertEquals(404, r.getStatus());
	}

}
