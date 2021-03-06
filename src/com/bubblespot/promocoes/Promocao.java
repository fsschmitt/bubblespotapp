package com.bubblespot.promocoes;

import android.graphics.Bitmap;

public class Promocao {

	private int id;
	private String data_final;
	private String desconto;
	private String detalhes;
	private String imagem_url;
	private int loja_id;
	private String loja_nome;
	private int shopping_id;
	private String shopping_nome;
	private String preco_inicial;
	private String preco_final;
	private String produto;
	private Bitmap bImage;
	private boolean primeira_s = false;
	private boolean primeira_l = false;

	public Promocao(int id, String data_final, String desconto, String detalhes, String imagem_url, int loja_id, String loja_nome, int shopping_id, String shopping_nome, String preco_inicial, String preco_final, String produto)
	{
		this.setId(id);
		this.setData_final(data_final);
		this.setDesconto(desconto);
		this.setDetalhes(detalhes);
		this.setImagem_url(imagem_url);
		this.setLoja_id(loja_id);
		this.setLoja_nome(loja_nome);
		this.setShopping_id(shopping_id);
		this.setShopping_nome(shopping_nome);
		this.setPreco_inicial(preco_inicial);
		this.setPreco_final(preco_final);
		this.setProduto(produto);
		this.setbImage(null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData_final() {
		return data_final;
	}

	public void setData_final(String data_final) {
		this.data_final = data_final;
	}

	public String getDesconto() {
		return desconto;
	}

	public void setDesconto(String desconto) {
		this.desconto = desconto;
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

	public int getLoja_id() {
		return loja_id;
	}

	public void setLoja_id(int loja_id) {
		this.loja_id = loja_id;
	}

	public String getPreco_inicial() {
		return preco_inicial;
	}

	public void setPreco_inicial(String preco_inicial) {
		this.preco_inicial = preco_inicial;
	}

	public String getPreco_final() {
		return preco_final;
	}

	public void setPreco_final(String preco_final) {
		this.preco_final = preco_final;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Bitmap getbImage() {
		return bImage;
	}

	public void setbImage(Bitmap bImage) {
		this.bImage = bImage;
	}

	public String getShopping_nome() {
		return shopping_nome;
	}

	public void setShopping_nome(String shopping_nome) {
		this.shopping_nome = shopping_nome;
	}

	public String getLoja_nome() {
		return loja_nome;
	}

	public void setLoja_nome(String loja_nome) {
		this.loja_nome = loja_nome;
	}

	public int getShopping_id() {
		return shopping_id;
	}

	public void setShopping_id(int shopping_id) {
		this.shopping_id = shopping_id;
	}

	public boolean isPrimeira_s() {
		return primeira_s;
	}

	public void setPrimeira_s(boolean primeira_s) {
		this.primeira_s = primeira_s;
	}

	public boolean isPrimeira_l() {
		return primeira_l;
	}

	public void setPrimeira_l(boolean primeira_l) {
		this.primeira_l = primeira_l;
	}
}
