'use strict';

angular.module( 'mutrack' )
    .controller( 'SaleCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http, $route ) {

        $scope.sale = {};
        $scope.sales = [];

        $scope.vendedor = {};
        $scope.vendedors = [];

        $scope.cart = {};
        $scope.carts = [];

        $scope.login = {};
        $scope.logins = [];

        $scope.game = {};
        $scope.games = [];

        $scope.client = {};
        $scope.clients = [];

        $scope.console = {};
        $scope.consoles = [];

        $scope.dinheiro = 0;
        $scope.totalvalue = 0;

        $scope.sale.quantidade = 0;

        $scope.showAddEditSale = false;

        $scope.buttonSale = false;
        $scope.buttonOtherSale = true;

        ngNotify.config( {
            theme: 'pastel'
        } );

        RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
            $scope.login = data;
            $scope.logins = data;

            $scope.vendedor = $scope.logins[4];
            
        } );  

        

        RestSrv.find( SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/getItensConsole', function ( data ) {
            $scope.carrinhoConsole = data;
            if($scope.carrinhoConsole.length == 0 && $scope.carts.length == 0){
                ngNotify.set( 'Carrinho vazio !', 'error' );
                $scope.btnFinishedBuy = true;
            }else{
                
                var values = $scope.carrinhoConsole;
                angular.forEach(values,function(value){          
                    var preco = value.precoConsole;
                    $scope.totalvalue = $scope.totalvalue + preco;
                    $scope.subTotal = $scope.totalvalue;
                });
                $scope.btnFinishedBuy = false;
                $scope.sale.quantidade  +=  $scope.carrinhoConsole.length;
            }
        });

        var deletarItemConsoleURL = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/deletarItemConsole';
        $scope.deleteConsole = function ( cart ) {
            RestSrv.delete( deletarItemConsoleURL, cart, function () {
                $scope.carrinhoConsole.splice( $scope.carrinhoConsole.indexOf( cart ), 1 );
                ngNotify.set( 'Item deletado', 'success' );
                $route.reload();
                if($scope.carrinhoConsole.length == 0){
                    $scope.btnFinishedBuy = true;
                }
            } );
        };
        
        RestSrv.find( SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/adicionarCarrinho', function ( data ) {
            $scope.carts = data;
            if($scope.carts.length == 0){
                ngNotify.set( 'Carrinho de jogos vazio !', 'error' );
                $scope.btnFinishedBuy = true;
            }else{
                var values = $scope.carts;
                angular.forEach(values,function(value){          
                    var preco = value.preco;
                    $scope.totalvalue = $scope.totalvalue + preco;
                    $scope.subTotal = $scope.totalvalue;
                });
                $scope.btnFinishedBuy = false;
                $scope.sale.quantidade += $scope.carts.length;
            }
        });
       
        $scope.show = function () {
            $scope.showAddEditSale = true;
        };

        
        $scope.hide = function () {
            $scope.showAddEditSale = false;
            $scope.sale = {};
        };

        
        var saleUrl = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda';

        
        

        $scope.editSale = function ( sale ) {
            $scope.sale = angular.copy( sale );
            $scope.show();
        };

        $scope.deleteSale = function ( sale ) {
            RestSrv.delete( saleUrl, sale, function () {
                $scope.sales.splice( $scope.sales.indexOf( sale ), 1 );
                if(emptyStock === false){
                    ngNotify.set( 'Venda deletada com sucesso.', 'success' );
                }else{
                    ngNotify.set( 'Venda não concretizada. Motivo: Não há estoque suficiente', 'error' );
                }
            } );
        };

        $scope.anotherSale = function(){
            $route.reload();
        }

		/* Método utilizado para realizar a venda, e por acaso realiza o cálculo caso dinheiro recebido for menor
		/  do que o subtotal da venda, exibe a mensagem na tela
		*/
        $scope.saveSale = function ( sale ) {

            $scope.troco = (sale.dinheiro - $scope.totalvalue)
            
            if($scope.troco < 0){
                ngNotify.set( 'Não foi possível realizar a venda, valor recebido inferior ao valor do game', 'error' );
            }else{
                RestSrv.add( saleUrl, sale, function ( newSale ) {
                    $scope.buttonSale = true;
                    $scope.buttonOtherSale = false;
                    ngNotify.set( 'Venda \ \ realizada com sucesso. Email enviado para o cliente com sucesso', 'success' );
                });  
            }      
        };
	
	
		// RETORNA OS DADOS DOS VENDEDORES
        $scope.buscaVendedor = function () {
            $http.get( 'http://localhost:8080/api/private/vendedor' ).then(
                function success( response ) {
                    $scope.vendedors = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaVendedor();
		
		
			
		
		// RETORNA OS DADOS DOS JOGOS
        $scope.buscaGames = function () {
            $http.get( 'http://localhost:8080/api/private/jogos' ).then(
                function success( response ) {
                    $scope.game = response.data;
                },
                function error( response ) {}
            );
        };

        $scope.buscaGames();

		// RETORNA OS DADOS DOS CLIENTES
        $scope.buscaCliente = function () {
            $http.get( 'http://localhost:8080/api/private/client/' ).then(
                function success( response ) {
                    $scope.client = response.data;
                },
                function error( response ) {}
            );
        };

        var deletar = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/deletarItem';
        $scope.deleteCart = function ( cart ) {
            RestSrv.delete( deletar, cart, function () {
                $scope.carts.splice( $scope.carts.indexOf( cart ), 1 );
                ngNotify.set( 'Item deletado', 'success' );
                $route.reload();

                if($scope.carts.length == 0){
                    ngNotify.set( 'Carrinho vazio !', 'error' );
                    $scope.btnFinishedBuy = true;
                }
            } );
        };

        $scope.buscaCliente();

		// ArrayList da forma de pagamento
        $scope.paymentForm = ['Dinheiro', 'Cartão Débito', 'Cartão Crédito'];
        
        $scope.update_select = function() {
            if($scope.sale.formPayment == 'Dinheiro'){
                $scope.disableMoneyEntry = false;
            }else{
                $scope.disableMoneyEntry = true;
            }
            
        };
        

    } );
