package com.bubblespot.testes;

import android.test.ActivityInstrumentationTestCase2;

import com.bubblespot.BubbleSpot;
import com.jayway.android.robotium.solo.Solo;

public class SoloTestes extends ActivityInstrumentationTestCase2<BubbleSpot>{

	private Solo solo;

	public SoloTestes() {
		super("com.bubblespot", BubbleSpot.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void getDolceVita(){
		//Imagem Shopppings
		solo.clickOnImage(2); 
		solo.assertCurrentActivity("Expected ListShoppings activity", "ListShoppings"); 
		//Shopping Dolce Vita
		solo.clickOnImage(3);
		solo.assertCurrentActivity("Expected ShoppingDetail activity", "ShoppingDetail");
	}

	public void testPromocoesShopping() throws Exception {
		getDolceVita();
		//Entrar na atividade das Promocoes
		solo.clickOnText("Promoções"); 
		solo.assertCurrentActivity("Expected ListPromo activity", "ListPromo");
		//Entrar na promocao MacBook Pro
		solo.clickOnText("MacBook Pro 13''");  
		solo.assertCurrentActivity("Expected PromoDetail activity", "PromoDetail");
		//Garantir que entra na promocao correta
		assertTrue(solo.searchText("MacBook Pro 13''"));
		assertTrue(solo.searchText("Fnac"));
		assertTrue(solo.searchText("Dolce Vita"));
		solo.goBack();
		//Verificar que volta para a listagem de promocoes
		solo.assertCurrentActivity("Expected ListPromo activity", "ListPromo");
		//Entrar na promocao Modern Warfare 3
		solo.clickOnText("Modern Warfare 3 PS3");
		solo.assertCurrentActivity("Expected PromoDetail activity", "PromoDetail");
		//Garantir que entra na promocao correta
		assertTrue(solo.searchText("Modern Warfare 3 PS3"));
		assertTrue(solo.searchText("Worten"));
		assertTrue(solo.searchText("Dolce Vita"));
		solo.goBack();
		//Verificar que o filtro filtra as promocoes corretamente
		solo.enterText(0, "Modern");
		assertTrue(solo.searchText("Modern Warfare 3 PS3"));
		assertFalse(solo.searchText("MacBook Pro 13''"));
	}

	public void testAgenda() throws Exception {
		getDolceVita();
		//Entrar na atividade da Agenda Cultural
		solo.clickOnText("Agenda Cultural");
		solo.assertCurrentActivity("Expected Cultural activity", "Cultural");

	}

	public void testCinema() throws Exception {
		getDolceVita();
		//Entrar na atividade do Cinema
		solo.clickOnText("Cinema");
		solo.assertCurrentActivity("Expected Cinema activity", "Cinema");
		//Verificar todos os filmes
		solo.searchText("Inception");
		solo.searchText("The art of getting by");
	}

	public void testPlanta() throws Exception {
		getDolceVita();
		//Entrar na atividade da Planta
		solo.clickOnText("Planta");
		solo.assertCurrentActivity("Expected Planta activity", "Touch");
	}

	public void testLojasShopping() throws Exception {
		getDolceVita();
		//Entrar na atividade das Lojas
		solo.clickOnText("Lojas");
		solo.assertCurrentActivity("Expected ListShops activity", "ListShops");
		//Verificar que o filtro filtra as promocoes corretamente
		solo.enterText(0, "Fnac");
		solo.clickOnImage(2);
		solo.assertCurrentActivity("Expected ShopDetail activity", "ShopDetail");
		//Verificar que se encontra na página da fnac
		assertTrue(solo.searchText("Fnac"));
		assertTrue(solo.searchText("Dolce Vita"));
		//Verificar a segunda promoção
		solo.clickOnImage(4);
		assertTrue(solo.searchText("Galaxy"));
		solo.goBack();
		solo.goBack();
		//Verificar o filtro para a worten
		solo.clearEditText(0);
		solo.enterText(0, "Worten");
		solo.clickOnImage(2);
		solo.assertCurrentActivity("Expected ShopDetail activity", "ShopDetail");
		assertTrue(solo.searchText("Worten"));
		assertTrue(solo.searchText("Dolce Vita"));
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Listar Promoções");
		assertTrue(solo.searchText("Modern Warfare 3 PS3"));
		assertFalse(solo.searchText("MacBook"));
	}

	public void testShopping() throws Exception {
		solo.clickOnImage(2);
		solo.assertCurrentActivity("Expected ListShoppings activity", "ListShoppings"); 
		solo.clickOnImage(3);
		solo.assertCurrentActivity("Expected ShoppingDetail activity", "ShoppingDetail");
	}

	public void testLojas(){
		//Imagem Lojas
		solo.clickOnImage(3); 
		solo.assertCurrentActivity("Expected ListAllShops activity", "ListAllShops"); 
		solo.clickOnText("Fnac");
		solo.assertCurrentActivity("Expected ShopDetail activity", "ShopDetail");
	}

	public void testLojasFiltro(){
		//Imagem Lojas
		solo.clickOnImage(3); 
		solo.assertCurrentActivity("Expected ListAllShops activity", "ListAllShops"); 
		solo.enterText(0, "Pull");
		assertFalse(solo.searchText("Fnac"));
		assertFalse(solo.searchText("Worten"));
	}

	public void testLojasFiltro2(){
		//Imagem Lojas
		solo.clickOnImage(3); 
		solo.assertCurrentActivity("Expected ListAllShops activity", "ListAllShops"); 
		solo.enterText(0, "Pull");
		solo.clickOnText("Pull");
		solo.assertCurrentActivity("Expected ShopDetail activity", "ShopDetail");
		assertTrue(solo.searchText("Pull"));
	}

	public void testFilmes(){
		//Imagem Filmes
		solo.clickOnImage(4); 
		solo.assertCurrentActivity("Expected ListAllCinema activity", "ListAllCinema"); 
		solo.clickOnText("Inception");
		solo.assertCurrentActivity("Expected Cinema activity", "Cinema");
	}

	public void testFilmesFiltro(){
		//Imagem Filmes
		solo.clickOnImage(4); 
		solo.assertCurrentActivity("Expected ListAllCinema activity", "ListAllCinema"); 
		solo.enterText(0, "Art");
		assertFalse(solo.searchText("Inception"));
	}

	public void testFilmesFiltro2(){
		//Imagem Filmes
		solo.clickOnImage(4); 
		solo.assertCurrentActivity("Expected ListAllCinema activity", "ListAllCinema"); 
		solo.enterText(0, "Art");
		solo.clickOnText("art of");
		solo.assertCurrentActivity("Expected Cinema activity", "Cinema");
		assertTrue(solo.searchText("Art"));
	}

	public void testEventos(){
		//Imagem Eventos
		solo.clickOnImage(5); 
		solo.assertCurrentActivity("Expected ListAllCultural activity", "ListAllCultural"); 
		solo.clickOnText("Jantar");
		solo.assertCurrentActivity("Expected Cultural activity", "Cultural");
	}

	public void testEventosFiltro(){
		//Imagem Eventos
		solo.clickOnImage(5); 
		solo.assertCurrentActivity("Expected ListAllCultural activity", "ListAllCultural"); 
		solo.enterText(0, "Natal");
		assertFalse(solo.searchText("123"));
	}

	public void testEventosFiltro2(){
		//Imagem Eventos
		solo.clickOnImage(5); 
		solo.assertCurrentActivity("Expected ListAllCultural activity", "ListAllCultural"); 
		solo.enterText(0, "Natal");
		solo.clickOnText("Jantar");
		solo.assertCurrentActivity("Expected Cultural activity", "Cultural");
		assertTrue(solo.searchText("Jantar"));
	}

	public void testPromos(){
		//Imagem Promos
		solo.clickOnImage(6); 
		solo.assertCurrentActivity("Expected ListAllPromo activity", "ListAllPromo"); 
		solo.clickOnText("Samsung");
		solo.assertCurrentActivity("Expected PromoDetail activity", "PromoDetail");
	}

	public void testPromosFiltro(){
		//Imagem Promos
		solo.clickOnImage(6); 
		solo.assertCurrentActivity("Expected ListAllPromo activity", "ListAllPromo"); 
		solo.enterText(0, "Mac");
		assertFalse(solo.searchText("Modern"));
		assertFalse(solo.searchText("Samsung"));
	}

	public void testPromosFiltro2(){
		//Imagem Promos
		solo.clickOnImage(6); 
		solo.assertCurrentActivity("Expected ListAllPromo activity", "ListAllPromo"); 
		solo.enterText(0, "Mac");
		solo.clickOnText("Pro");
		solo.assertCurrentActivity("Expected PromoDetail activity", "PromoDetail");
		assertTrue(solo.searchText("Mac"));
	}

	@Override
	public void tearDown() throws Exception {
		//Robotium will finish all the activities that have been opened
		solo.finishOpenedActivities();
	}
}
