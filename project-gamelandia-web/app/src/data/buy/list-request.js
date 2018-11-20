'use strict';

angular.module( 'mutrack' )
    .controller( 'ListBuyCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {


        $scope.request = {};
        $scope.requests = [];


        ngNotify.config( {
            theme: 'pastel'
        } );

        
        var transactionsURL = SERVICE_PATH.PRIVATE_PATH + '/transactions'
        var infoUrl = SERVICE_PATH.PRIVATE_PATH + '/transactions/infoBuy';

        RestSrv.find( transactionsURL, function ( data ) {
            $scope.requests = data;
            console.log($scope.requests)
            angular.forEach($scope.requests,function(value,key){
                console.log(value.generateNote)
                if(value.generateNote == true){
                    $scope.notaGerada = "Sim";
                }

            });
            
        } );

      
        $scope.showDataBuy = function (code){
            var data = {code:code}
            $http({url: infoUrl,method: "GET",params: data}).then(
                function successCallback(response) {  
                  $scope.return = response.data;  
                  ngNotify.set( "O Estoque foi atualizado e o email foi enviado com sucesso", 'sucess' );                                     
            },
            function errorCallback(response) {
                ngNotify.set( response.data.message, 'error' );          
            });
        }

} );
