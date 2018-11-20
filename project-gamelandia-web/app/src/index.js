'use strict';

var BASE_URL = 'http://localhost:8080/api';

// Inserção de dependências e os caminhos das páginas
angular.module( 'mutrack', [ 'checklist-model', 'ngNotify', 'ngRoute', 'ngCookies', 'ngMask', 'ngStorage', 'chart.js', 'angularUtils.directives.dirPagination' ] )
    .constant( 'SERVICE_PATH', {
        'ROOT_PATH': BASE_URL,
        'PUBLIC_PATH': BASE_URL + '/public',
        'PRIVATE_PATH': BASE_URL + '/private'
    } )
    .config( function ( $routeProvider ) {
        $routeProvider.
            when( '/home', {
                templateUrl: 'src/home/home.html',
                controller: 'HomeCtrl'
            } )
           .when( '/vendedor', {
                templateUrl: 'src/data/vendedor/vendedor.html',
                controller: 'VendedorCtrl'
            } )
            .when( '/vendedor-list', {
                templateUrl: '/src/data/vendedor/vendedor_list.html',
                controller: 'VendedorCtrl'
            } )
            .when( '/client', {
                templateUrl: 'src/data/cliente/cliente.html',
                controller: 'ClientCtrl'
            } )
            .when( '/jogos', {
                templateUrl: 'src/data/game/game.html',
                controller: 'GameCtrl',
                controllerAs: 'vmGameCtrl'
            } )
            .when( '/console', {
                templateUrl: 'src/data/console/console.html',
                controller: 'ConsoleCtrl'
            } )
            .when( '/realizar-venda', {
                templateUrl: 'src/data/sale/sale.html',
                controller: 'SaleCtrl'
            } )
            .when( '/realizar-pedido', {
                templateUrl: 'src/data/buy/buy.html',
                controller: 'BuyCtrl'
            } )
            .when( '/carrinho', {
                templateUrl: 'src/data/cart/cart.html',
                controller: 'AddCartCtrl'
            } )
            .when( '/listar-pedidos', {
                templateUrl: 'src/data/buy/list-request.html',
                controller: 'ListBuyCtrl'
            } )
            .when( '/jogos/client', {
                templateUrl: 'src/data/game/game_client.html',
                controller: 'GameClientCtrl'
            } )
            .when( '/registrar', {
                templateUrl: 'src/data/cliente/register.html',
                controller: 'ClientRegisterCtrl'
            } )
            .when( '/minhaConta', {
                templateUrl: 'src/data/cliente/minhaConta.html',
                controller: 'MyAccountCtrl'
            } )
            .when( '/meusPedidos', {
                templateUrl: 'src/data/cliente/myRequests.html',
                controller: 'RequestCtrl'
            } )
            .when( '/relatorioVendas', {
                templateUrl: 'src/data/vendedor/reportEmployee.html',
                controller: 'ReportCtrl'
            } )
            .when( '/console', {
                templateUrl: 'src/data/console/console.html',
                controller: 'ConsoleCtrl'
            } )
            .otherwise({
                redirectTo: '/login',
                templateUrl: 'src/login/login.html',
                controller: 'LoginCtrl'
              });

              
    } )

    .config(function($httpProvider) {
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        $httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = 'XMLHttpRequest';
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.interceptors.push('httpRequestInterceptor');
      })

    .run(function($rootScope, ngNotify, LoginLogoutSrv) {
        $rootScope.authDetails = { name: '', authenticated: false, permissions: [] };

        LoginLogoutSrv.verifyAuth();
    } )
    
    
    
    ;
