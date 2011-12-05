package com.bubblespot;

import android.graphics.Bitmap;

public class Filme {

	private String nome;
	private int idShopping;
	private String detalhes;
	private String horarios;
	private Bitmap bImage;
	private String image_url;
	private String trailer;
	private String sala;

	Filme(int idShopping,String nome, String detalhes, String sala, String horarios, String image_url, String trailer){
		this.setIdShopping(idShopping);
		this.setNome(nome);
		this.setDetalhes(detalhes);
		this.setHorarios(horarios);
		this.setImage_url(image_url);
		this.setTrailer(trailer);
		this.setSala(sala);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdShopping() {
		return idShopping;
	}

	public void setIdShopping(int idShopping) {
		this.idShopping = idShopping;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getHorarios() {
		return horarios;
	}

	public void setHorarios(String horarios) {
		this.horarios = horarios;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Bitmap getbImage() {
		return bImage;
	}

	public void setbImage(Bitmap bImage) {
		this.bImage = bImage;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}
}

