'use strict';

angular.module( 'mutrack' )
    .controller( 'VendedorCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {
        $scope.vendedor = {};
        $scope.vendedors = [];
        $scope.showAddEditVendedor = false;

        
        $scope.estados = ['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS',
        'RO','RR','SC','SP','SE','TO']

        ngNotify.config( {
            theme: 'pastel'
        } );

        $scope.inputType = 'password';
  
        $scope.hideShowPassword = function(){
          if ($scope.inputType == 'password')
            $scope.inputType = 'text';
          else
            $scope.inputType = 'password';
        };

        $scope.show = function () {
            $scope.showAddEditVendedor = true;
        };

        // Hide the form used to edit or add vendedors.
        $scope.hide = function () {
            $scope.showAddEditVendedor = false;
            $scope.vendedor = {};
        };

     

        

        // Manage CRUD operations.
        var vendedorUrl = SERVICE_PATH.PRIVATE_PATH + '/vendedor';

        $scope.editVendedor = function ( vendedor ) {
            $scope.vendedor = angular.copy( vendedor );
            $scope.show();
        };

        $scope.deleteVendedor = function ( vendedor ) {
            RestSrv.delete( vendedorUrl, vendedor, function () {
                $scope.vendedors.splice( $scope.vendedors.indexOf( vendedor ), 1 );
                ngNotify.set( 'Vendedor \'' + vendedor.name + '\' deletado.', 'success' );
            } );
        };
        

        $scope.saveVendedor = function ( vendedor ) {
            if ( vendedor.id ) {
                RestSrv.edit( vendedorUrl, vendedor, function () {
                    for ( var i = 0; i < $scope.vendedors.length; i++ ) {
                        if ( $scope.vendedors[ i ].id === vendedor.id ) {
                            $scope.vendedors[ i ] = vendedor;
                        }
                    }
                    $scope.hide();
                    ngNotify.set( 'Vendedor  \'' + vendedor.name + '\' atualizado.', 'success' );
                } );
            } else {
                RestSrv.add( vendedorUrl, vendedor, function ( newEmployee ) {
                    $scope.vendedors.push( newEmployee );
                    $scope.showAddEditVendedor = false;
                    ngNotify.set( 'Vendedor \'' + vendedor.name + '\' adicionado.', 'success' );
                    
                   

                    $route.reload();
                } );
            }
        };

          // Request all data (permission and user).
          var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';

    RestSrv.find(permissionUrl, function(data) {
      $scope.permissions = data;

      RestSrv.find( vendedorUrl, function ( data ) {
        $scope.vendedors = data;
    } );
    
    });
});
