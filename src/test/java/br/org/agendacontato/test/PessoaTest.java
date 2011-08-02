package br.org.agendacontato.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.agendacontatos.model.Pessoa;

/**
 * Testa a entidade Pessoa e o acesso ao banco de dados
 * TODO: Polir o código, principalmente a parte relacionada a busca de pessoas
 */
public class PessoaTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;

	@BeforeClass
	public static void inicializaEm() throws Exception {
		emf = Persistence.createEntityManagerFactory("AgendaContatos");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	@AfterClass
	public static void finalizaEm() {
		em.close();
		emf.close();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void criaPessoa() {
		Pessoa p = new Pessoa("Jesuino", "5556960", "jesuino@gmail.com",
				"http://jesuino.com.br", "Rua java, 100", new Date());
		tx.begin();
		em.persist(p);
		tx.commit();
		List<Pessoa> pessoas = em.createQuery("from Pessoa").getResultList();
		assertNotSame(0, pessoas.size());
		assertEquals(pessoas.get(0).getNome(), "Jesuino");
	}

	@Test
	public void alteraPessoa() {
		Pessoa p = (Pessoa) em.createQuery("from Pessoa").getResultList()
				.get(0);
		String velhoEndereco = p.getEndereco();
		p.setEndereco("Novo Endereço...");
		tx.begin();
		em.persist(p);
		tx.commit();
		p = (Pessoa) em.createQuery("from Pessoa").getResultList().get(0);
		assertNotSame(p.getEndereco(), velhoEndereco);
	}

	@Test
	public void removePessoa() {
		Pessoa p = (Pessoa) em.createQuery("from Pessoa").getResultList()
				.get(0);
		tx.begin();
		em.remove(p);
		tx.commit();
		assertEquals(em.createQuery("from Pessoa").getResultList().size(), 0);
	}

}
