package com.bubblespot;

public class Evento {

	private int idShopping;
	private String nome;
	private String data;
	private String detalhes;
	
	Evento(int idShopping, String nome, String data, String detalhes){
		this.idShopping = idShopping;
		this.nome = nome;
		this.data = data;
		this.detalhes = detalhes;
	}

	public int getIdShopping() {
		return idShopping;
	}

	public void setIdShopping(int idShopping) {
		this.idShopping = idShopping;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	
}
