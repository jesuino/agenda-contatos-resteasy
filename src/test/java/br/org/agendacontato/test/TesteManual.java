package br.org.agendacontato.test;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

import br.org.agendacontatos.resource.impl.PessoaResourceImpl;

public class TesteManual {
	public static void main(String[] args) {
		TJWSEmbeddedJaxrsServer tjws = new TJWSEmbeddedJaxrsServer();
		tjws.setPort(8081);
		tjws.getDeployment().getActualResourceClasses()
				.add(PessoaResourceImpl.class);
		tjws.start();
	}
}
