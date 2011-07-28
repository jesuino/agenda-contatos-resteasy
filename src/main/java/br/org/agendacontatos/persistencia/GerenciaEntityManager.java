package br.org.agendacontatos.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GerenciaEntityManager {
	private static EntityManager em;
	private static EntityManagerFactory emf;

	public static synchronized EntityManager getEntityManager() {
		if (em == null) {
			if (emf == null)
				emf = Persistence.createEntityManagerFactory("AgendaContatos");
			em = emf.createEntityManager();
		}
		return em;
	}

}
