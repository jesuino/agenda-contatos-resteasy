package br.org.agendacontato.test;

import static org.junit.Assert.assertNotSame;

import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.agendacontatos.model.Pessoa;

public class PessoaTest {

	private EntityManagerFactory emf;

	private EntityManager em;

	private Pessoa pessoa;

	@Before
	public void preparaTeste() {
		emf = Persistence.createEntityManagerFactory("AgendaContatos");
		em = emf.createEntityManager();
		pessoa = new Pessoa("Jesuino", "5556960", "jesuino@gmail.com",
				"http://jesuino.com.br", "Rua java, 100", new Date());
	}

	@After
	public void finalizaTeste() {
		
	}

	@Test
	public void criaPessoa() {
		em.getTransaction().begin();
		pessoa.setId(1);
		em.persist(pessoa);
		em.getTransaction().commit();
		assertNotSame(Collections.EMPTY_LIST, em.createQuery("from Pessoa").getResultList());
	}

	@Test
	public void alteraPessoa() {

	}

	@Test
	public void removePessoa() {

	}

	@Test
	public void buscaPessoa() {

	}

}
