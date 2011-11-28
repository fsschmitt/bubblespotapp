package com.bubblespot;

public class Promocao {
	
	private int id;
	private String data_final;
	private String desconto;
	private String detalhes;
	private String imagem_url;
	private int loja_id;
	private String preco_inicial;
	private String preco_final;
	private String produto;
	
	Promocao(int id, String data_final, String desconto, String detalhes, String imagem_url, int loja_id, String preco_inicial, String preco_final, String produto)
	{
		this.setId(id);
		this.setData_final(data_final);
		this.setDesconto(desconto);
		this.setDetalhes(detalhes);
		this.setImagem_url(imagem_url);
		this.setLoja_id(loja_id);
		this.setPreco_inicial(preco_inicial);
		this.setPreco_final(preco_final);
		this.setProduto(produto);
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

}
