package com.bubblespot.shoppings;

import android.graphics.Bitmap;

public class Shopping {

	private String latitude;
	private String longitude;
	private String descricao;
	private String localizacao;
	private String nome;
	private String telefone;
	private String email;
	private String imagem_url;
	private String planta;
	private double dist;
	private int id;
	private Bitmap bImage;

	Shopping(int id,String nome, String localizacao, String descricao, String telefone, String email, String latitude, String longitude, String image_url,String planta, double dist){

		this.setNome(nome);
		this.setLocalizacao(localizacao);
		this.setDescricao(descricao);
		this.setTelefone(telefone);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setEmail(email);
		this.setImagem_url(image_url);
		this.setDist(dist);
		this.setId(id);
		this.setPlanta(planta);
	}

	public Shopping(int id,String nome, String localizacao, String descricao, String telefone, String email, String latitude, String longitude, String image_url, String planta){

		this.setNome(nome);
		this.setLocalizacao(localizacao);
		this.setDescricao(descricao);
		this.setTelefone(telefone);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setEmail(email);
		this.setImagem_url(image_url);
		this.setId(id);
		this.setPlanta(planta);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setImagem_url(String imagem_url) {
		this.imagem_url = imagem_url;
	}

	public String getImagem_url() {
		return imagem_url;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Bitmap getbImage() {
		return bImage;
	}

	public void setbImage(Bitmap bImage) {
		this.bImage = bImage;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}
}
