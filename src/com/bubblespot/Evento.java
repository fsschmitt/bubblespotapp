package com.bubblespot;

import android.graphics.Bitmap;

public class Evento {

	private int idShopping;
	private String nome;
	private String data;
	private String detalhes;
	private String imagem_url;
	private Bitmap bImage;
	
	Evento(int idShopping, String nome, String data, String detalhes, String imagem_url){
		this.idShopping = idShopping;
		this.nome = nome;
		this.data = data;
		this.detalhes = detalhes;
		this.imagem_url = imagem_url;
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

	public String getImagem_url() {
		return imagem_url;
	}

	public void setImagem_url(String imagem_url) {
		this.imagem_url = imagem_url;
	}

	public Bitmap getbImage() {
		return bImage;
	}

	public void setbImage(Bitmap bImage) {
		this.bImage = bImage;
	}
	
}
