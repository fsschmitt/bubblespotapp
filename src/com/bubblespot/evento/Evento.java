package com.bubblespot.evento;

import android.graphics.Bitmap;

public class Evento {

	private int id;
	private int idShopping;
	private String nome;
	private String data;
	private String detalhes;
	private String imagem_url;
	private Bitmap bImage;
	private String local;
	private String nomeShopping;
	private boolean primeira = false;

	Evento(int id, int idShopping, String nomeShopping, String nome, String data, String local, String detalhes, String imagem_url){
		this.setId(id);
		this.setIdShopping(idShopping);
		this.setShopping(nomeShopping);
		this.setNome(nome);
		this.setData(data);
		this.setLocal(local);
		this.setDetalhes(detalhes);
		this.setImagem_url(imagem_url);
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public boolean isPrimeira() {
		return primeira;
	}

	public void setPrimeira(boolean primeira) {
		this.primeira = primeira;
	}

	public String getShopping() {
		return nomeShopping;
	}

	public void setShopping(String nomeShopping) {
		this.nomeShopping = nomeShopping;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}