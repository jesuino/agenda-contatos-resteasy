package br.org.agendacontatos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Pessoa {
	
	@Id
	@GeneratedValue
    private int id;
    
    private String nome;
    
    private String telefone;
    
    private String email;
    
    private String endereco;
    
    private Date dataCriacao;
    
    private String url;
    
    public Pessoa(String nome, String telefone, String email, String url,
            String endereco, Date dataCriacao) {
        super();
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.url = url;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
    }

    public Pessoa() {
        super();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
