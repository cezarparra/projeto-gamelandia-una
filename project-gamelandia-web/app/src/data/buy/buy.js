'use strict';

angular.module( 'mutrack' )
    .controller( 'BuyCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {

        var currentUserLogin = "http://localhost:8080/api/private/teste"

        $scope.buy = {};
        $scope.buys = [];

        $scope.currentUser = {};
        $scope.currentUsers = [];

        $scope.vendedor = {};
        $scope.vendedors = [];

        $scope.game = {};
        $scope.games = [];

        $scope.client = {};
        $scope.clients = [];

        $scope.console = {};
        $scope.consoles = [];
 

        var emptyStock = false;

        $scope.showAddEditBuy = false;

        ngNotify.config( {
            theme: 'pastel'
        } );

        // Utilizado para exibir o formulário na página
        $scope.show = function () {
            $scope.showAddEditBuy = true;
        };

        // Esconder o formulário na página
        $scope.hide = function () {
            $scope.showAddEditBuy = false;
            $scope.buy = {};
        };

       
        var buyUrl = SERVICE_PATH.PRIVATE_PATH + '/realizar-pedido';

        $scope.editBuy = function ( buy ) {
            $scope.buy = angular.copy( buy );
            $scope.show();
        };

		// Método utiilizado para excluir algum dado
        $scope.deleteBuy = function ( buy ) {
            RestSrv.delete( saleUrl, buy, function () {
                $scope.buys.splice( $scope.buys.indexOf( buy ), 1 );
                if(emptyStock === false){
                    ngNotify.set( 'Pedido deletado com sucesso.', 'success' );
                }else{
                    ngNotify.set( 'Pedido não concretizado. Motivo: Não há estoque suficiente', 'error' );
                }
            } );
        };

		// Método utiilizado para excluir algum dado
        $scope.saveBuy = function ( buy ) {
            if ( buy.id ) {
                RestSrv.edit( buyUrl, buy, function () {
                    delete buy.password;

                    for ( var i = 0; i < $scope.buys.length; i++ ) {
                        if ( $scope.buys[ i ].id === buy.id ) {
                            $scope.buys[ i ] = buy;
                        }
                    }

                    $scope.hide();
                    
                    ngNotify.set( 'Pedido atualizado com sucesso.', 'success' );
                } );
            } else {  
                RestSrv.add( buyUrl, buy, function ( newBuy ) {
                    $scope.buys.push( newBuy );
                    $scope.hide();
                    if(newBuy.quantidade <= 0){
                        emptyStock = true;
                        $scope.deleteBuy(newBuy);
                    }else{
                        ngNotify.set( 'Pedido realizado com sucesso. Em breve, Será enviado um email para confirmar sua compra', 'success' );
                    }
                } );

            }


            $scope.refresh = function() {
                $scope.buy = buy.query();
            };
        };

        
        var buyUrl = SERVICE_PATH.PRIVATE_PATH + '/realizar-pedido';

        RestSrv.find( buyUrl, function ( data ) {
            $scope.buys = data;

            RestSrv.find( buyUrl, function ( data ) {
                $scope.buys = data;
            } );
        } );


        $scope.buscaVendedor = function () {
            $http.get( 'http://localhost:8080/api/private/vendedor' ).then(
                function success( response ) {
                    $scope.vendedors = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaVendedor();

        $scope.buscaGames = function () {
            $http.get( 'http://localhost:8080/api/private/jogos' ).then(
                function success( response ) {
                    $scope.game = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaGames();

        $scope.buscaCliente = function () {
            $http.get( 'http://localhost:8080/api/private/client/' ).then(
                function success( response ) {
                    $scope.client = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaCliente();

        $scope.buscaConsoles = function () {
            $http.get( 'http://localhost:8080/api/private/console' ).then(
                function success( response ) {
                    $scope.console = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaConsoles();

        $scope.hasSent = ["Não", "Sim"];

        $scope.date = new Date();

        RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
            $scope.login = data;
            $scope.logins = data;

            $scope.client = $scope.logins[4];
            
        } );    

    } );
