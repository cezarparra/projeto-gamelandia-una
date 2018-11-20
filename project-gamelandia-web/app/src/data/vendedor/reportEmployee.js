'use strict';

angular.module( 'mutrack' )
    .controller( 'ReportCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {
        $scope.vendedor = {};
        $scope.vendedors = [];
        $scope.totalAmount = {};
        $scope.allSaleInfo = [];
        

        $scope.totalVenda = 0;
		$scope.jogosTotal = 0;

        $scope.showInfoSale = true;
        
        

        ngNotify.config( {
            theme: 'pastel'
        } );

        $scope.show = function () {
            $scope.showGraphic = true;
        };

        $scope.hide = function () {
            $scope.showGraphic = false;
        };
            
        var vendedorUrl = SERVICE_PATH.PRIVATE_PATH + '/vendedor';
        var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';
        var findSaleURL = SERVICE_PATH.PRIVATE_PATH + '/vendedor/findSaleDate';
        var gerarRelatorioURL = SERVICE_PATH.PRIVATE_PATH + '/vendedor/gerarRelatorioPDF';
        var findSaleBetweenURL = SERVICE_PATH.PRIVATE_PATH + '/vendedor/findSaleDateBetween';

        


  

        $scope.gerarRelatorio = function(){
            $scope.idVendas = [];
            angular.forEach($scope.allSaleInfo,function(value){
                
                $scope.idVendas.push(value.id); 
        });

        var data = {'vendaID[]':$scope.idVendas, 'montanteVenda':$scope.totalVenda,
        'quantidadeTotal': $scope.allSaleInfo.length, 'listaJogos[]':$scope.vendaJogo,'listaConsole[]':$scope.vendaConsole}
        $http({url: gerarRelatorioURL,method: "GET", params: data}).then(
            function successCallback(response) {
                ngNotify.set( 'Relatório gerado com sucesso', 'success' );
        }); 
                     
        }

        

     		// Método utilizado para realizar a busca entre 2 datas digitadas
        $scope.findSalePerDateBetween = function(date1, date2){
            var data = {'data1':date1, 'data2':date2}
            $http({url: findSaleBetweenURL,method: "GET",params: data}).then(
                function successCallback(response) {
                    $scope.allSaleInfo = [];
                    $scope.vendaJogo = [];
                    $scope.vendaConsole = [];
                    $scope.allSaleInfo = response.data;
                    $scope.showInfoSale = false;
					
					angular.forEach($scope.allSaleInfo,function(value){
                        $scope.totalVenda += value.precoVenda;
                        $scope.jogosVenda = value.game;
                        $scope.consoleVenda = value.console;
                });
                    angular.forEach($scope.jogosVenda,function(value){
                        $scope.vendaJogo.push(value.nomeJogo);
                        
                        
                    });
                    angular.forEach($scope.consoleVenda,function(value){
                        $scope.vendaConsole.push(value.nomeConsole);
                        
                    });
                    if($scope.allSaleInfo.length == 0){
                        $scope.showInfoSale = true;
                        ngNotify.set( 'Não foi encontrada nenhuma venda', 'error' );     
                    }    
            });          
        }

        var saleUrl = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda';
        RestSrv.find( saleUrl, function ( data ) {
            $scope.sales = data;

            RestSrv.find( saleUrl, function ( data ) {
                $scope.sales = data;
            } );
        } );

        RestSrv.find(permissionUrl, function(data) {
            $scope.permissions = data;

        RestSrv.find( vendedorUrl, function ( data ) {
            $scope.vendedors = data;
    } );

  
    });
});
