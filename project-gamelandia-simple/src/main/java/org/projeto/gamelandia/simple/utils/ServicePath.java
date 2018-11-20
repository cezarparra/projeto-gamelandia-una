package org.projeto.gamelandia.simple.utils;

public final class ServicePath {

	public static final String ALL = "/**";

	public static final String ROOT_PATH = "/api";

	public static final String PUBLIC_ROOT_PATH = ROOT_PATH + "/public";

	public static final String PRIVATE_ROOT_PATH = ROOT_PATH + "/private";

	public static final String VENDEDOR_PATH = PRIVATE_ROOT_PATH + "/vendedor";

	public static final String PERMISSION_PATH = PRIVATE_ROOT_PATH + "/permission";

	public static final String CLIENTE_PATH = PRIVATE_ROOT_PATH + "/client";

	public static final String GAME_PATH = PRIVATE_ROOT_PATH + "/jogos";
	
	public static final String CONSOLE_PATH = PRIVATE_ROOT_PATH + "/console";

	public static final String SALE_PATH = PRIVATE_ROOT_PATH + "/realizar-venda";
	
	public static final String REQUEST_PATH = PRIVATE_ROOT_PATH + "/transactions";
	
	public static final String REQUEST_DATA = PRIVATE_ROOT_PATH + "/requestData";

	public static final String BUY_PATH = PRIVATE_ROOT_PATH + "/checkout";
	
	public static final String CART_PATH = PRIVATE_ROOT_PATH + "/cart";
	
	public static final String FILEUP_PATH = PRIVATE_ROOT_PATH + "/fileUpload";

	public static final String LOGIN_PATH = PUBLIC_ROOT_PATH + "/login";

	public static final String LOGOUT_PATH = PUBLIC_ROOT_PATH + "/logout";
	

}