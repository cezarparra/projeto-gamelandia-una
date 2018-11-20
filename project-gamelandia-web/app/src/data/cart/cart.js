'use strict';

angular.module( 'mutrack' )
    .controller( 'AddCartCtrl', function ( $route, $scope, ngNotify, RestSrv, SERVICE_PATH, $http, $window ) {
        
        $scope.cart = {};
        $scope.carts = [];

        $scope.currentUser = {};
        $scope.currentUsers = [];

        $scope.vendedor = {};
        $scope.vendedors = [];

        $scope.game = {};
        $scope.games = [];

        $scope.client = {};
        $scope.clients = [];

    
        var emptyStock = false;

        $scope.btnFinishedBuy = true;
        $scope.btnCheckout = true;
        

        ngNotify.config( {
            theme: 'pastel'
        } );

        
        $scope.show = function () {
            $scope.showAddEditCart = true;
        };

        $scope.hide = function () {
            $scope.showAddEditCart = false;
            $scope.cart = {};
        };

        
        var addCartUrl = SERVICE_PATH.PRIVATE_PATH + '/cart/addCart';
        var updStockUrl = SERVICE_PATH.PRIVATE_PATH + '/cart/checkoutItems';
        

       $scope.editCart = function ( cart ) {
            $scope.cart = angular.copy( cart );
            $scope.show();
        };

		/* Método utilizado para que retorne os itens no carrinho, subtotal dos itens, e faz o redirecionamento para
		*	o site do PagSeguro
		*
		*/
        $scope.finishBuy = function(cart){

            RestSrv.find( addCartUrl, function ( data ) {
                $scope.carts = data;
                $scope.gamesIDList = [];
                var items = $scope.carts;
                
                    $scope.totalvalue = 0;
                    var values = $scope.carts;
                    angular.forEach(values,function(value){
                      
                        $scope.gamesIDList.push(value.id);
                        var preco = value.preco;
                        $scope.totalvalue = $scope.totalvalue + preco;
                    });
                    var data = {'gameid[]':$scope.gamesIDList}
                    $http({url: updStockUrl,method: "POST",params: data}).then(
                        function successCallback(response) {
                           while ($scope.carts.length !== 0) {
                            $scope.carts.splice( $scope.carts.indexOf( cart ), 1 );
                           }
                          $scope.url = response.data.url;
						  var urlPagSeguro = $scope.url;
						  $window.open(urlPagSeguro, '_blank');
                          $scope.btnCheckout = false;
                          ngNotify.set( 'Checkout realizado com sucesso', 'success' );
                        },
                        function errorCallback(response) {
                            ngNotify.set( response.data.message, 'error' );          
                        });
                    
                    $scope.btnFinishedBuy = false;
            });  
        };
		
		
		     
 
        $scope.showDataGame = function () {
            $scope.showAddEditGame = false;
            $scope.showGames = true;
        };

        RestSrv.find( addCartUrl, function ( data ) {
            $scope.carts = data;
            if($scope.carts.length == 0){
                ngNotify.set( 'Carrinho vazio !', 'error' );
                $scope.btnFinishedBuy = true;
            }else{
                $scope.totalvalue = 0;
                var values = $scope.carts;
                angular.forEach(values,function(value,key){
                    angular.forEach(value,function(v1,k1){
                    });
                    
                    var preco = value.preco;
                    $scope.totalvalue = $scope.totalvalue + preco;
                });
                $scope.btnFinishedBuy = false;
                
            }
        });
		
		// Método utlizado para excluir itens no carrinho e se estiver vazio exibe mensagem na tela
        $scope.deleteCart = function ( cart ) {
            RestSrv.delete( addCartUrl, cart, function () {
                $scope.carts.splice( $scope.carts.indexOf( cart ), 1 );
                ngNotify.set( 'Item deletado', 'success' );
                $route.reload();

                if($scope.carts.length == 0){
                    ngNotify.set( 'Carrinho vazio !', 'error' );
                    $scope.btnFinishedBuy = true;
                }
            } );
        };


        $scope.viewGame = function ( cart ) {
            $scope.cart = angular.copy(cart );
            $scope.showDataGame();
        };

        $scope.hasSent = ["Não", "Sim"];

        $scope.date = new Date();

        RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
            $scope.login = data;
            $scope.logins = data;

            $scope.client = $scope.logins[4];
            
        } );    

        $scope.paymentForm = ['Boleto', 'Débito', 'Crédito'];

    } );
