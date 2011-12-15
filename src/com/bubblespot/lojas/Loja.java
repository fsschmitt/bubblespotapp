package com.bubblespot.lojas;

import android.graphics.Bitmap;

public class Loja {

	private int id;
	private String nome;
	private int piso;
	private int numero;
	private String telefone;
	private String detalhes;
	private String imagem;
	private String tags;
	private String shopping;
	private int idShopping;
	private boolean primeira=false;
	private Bitmap bImage;

	public Loja (int id, String nome, int piso, int numero, String telefone, String detalhes, String imagem, String tags, String shopping, int idShopping){
		this.id = id;
		this.nome=nome;
		this.piso=piso;
		this.numero=numero;
		this.telefone=telefone;
		this.detalhes=detalhes;
		this.imagem=imagem;
		this.tags=tags;
		this.shopping=shopping;
		this.idShopping=idShopping;
		this.bImage=null;
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isPrimeira() {
		return primeira;
	}
	
	public void setPrimeira(boolean flag) {
		this.primeira = flag;
	}
	
	public int getIdShopping() {
		return idShopping;
	}
	
	public void setIdShopping(int idShopping) {
		this.idShopping = idShopping;
	}
	
	public Bitmap getbImage() {
		return bImage;
	}
	
	public void setbImage(Bitmap bImage) {
		this.bImage = bImage;
	}
}
