package com.bubblespot;

public class Loja {

	private String nome;
	private int piso;
	private int numero;
	private String telefone;
	private String detalhes;
	private String imagem;
	private String tags;
	private String shopping;
	
	Loja (String nome, int piso, int numero, String telefone, String detalhes, String imagem, String tags, String shopping){
		this.nome=nome;
		this.piso=piso;
		this.numero=numero;
		this.telefone=telefone;
		this.detalhes=detalhes;
		this.imagem=imagem;
		this.tags=tags;
		this.shopping=shopping;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPiso() {
		return piso;
	}
	public void setPiso(int piso) {
		this.piso = piso;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getShopping() {
		return shopping;
	}

	public void setShopping(String shopping) {
		this.shopping = shopping;
	}
	
	

}