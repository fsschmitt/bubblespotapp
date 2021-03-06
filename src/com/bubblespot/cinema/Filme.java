package com.bubblespot.cinema;

import android.graphics.Bitmap;

public class Filme {

	private int id;
	private String nome;
	private int idShopping;
	private String detalhes;
	private String horarios;
	private Bitmap bImage;
	private Bitmap bTrailer;
	private String image_url;
	private String trailer;
	private String sala;
	private String nomeShopping;
	private int pos;
	private boolean primeira=false;

	Filme(int id, int idShopping,String nomeShopping,String nome, String detalhes, String sala, String horarios, String image_url, String trailer){
		this.setId(id);
		this.setIdShopping(idShopping);
		this.setNome(nome);
		this.setDetalhes(detalhes);
		this.setHorarios(horarios);
		this.setImage_url(image_url);
		this.setTrailer(trailer);
		this.setSala(sala);
		this.setShopping(nomeShopping);
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

	public String getShopping() {
		return nomeShopping;
	}

	public void setShopping(String nomeShopping) {
		this.nomeShopping = nomeShopping;
	}

	public boolean isPrimeira() {
		return primeira;
	}

	public void setPrimeira(boolean primeira) {
		this.primeira = primeira;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Bitmap getbTrailer() {
		return bTrailer;
	}

	public void setbTrailer(Bitmap bTrailer) {
		this.bTrailer = bTrailer;
	}
}

