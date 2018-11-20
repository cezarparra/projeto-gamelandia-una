'use strict';
// INSERÇÂO DE DEPENDENCIAS
var gamelandiaJS = angular.module('mutrack');

gamelandiaJS.controller( 'ClientCtrl', function ($scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {

        $scope.client = {};
        $scope.clients = [];
        $scope.permissions = [];
        $scope.showAddEditClient = false;

        ngNotify.config( {
            theme: 'pastel'
        } );

		
        RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
                $scope.games = data;
        } );    

        
        $scope.show = function () {
            $scope.showAddEditClient = true;
        };

       
        $scope.hide = function () {
            $scope.showAddEditClient = false;
            $scope.client = {};
        };

        var clientUrl = SERVICE_PATH.PRIVATE_PATH + '/client';

		// Usado para editar o cadastro do cliente
        $scope.editClient = function ( client ) {
            $scope.client = angular.copy( client );
            $scope.show();
        };
		
		// Usado para deletar algum o cadastro do cliente
        $scope.deleteClient = function ( client ) {
            RestSrv.delete( clientUrl, client, function () {
                $scope.clients.splice( $scope.clients.indexOf( client ), 1 );
                ngNotify.set( 'Cliente \'' + client.name + '\' deletado.', 'success' );
            } );
        };
        
		// Usado para salvar algum o cadastro do cliente
        $scope.saveClient = function ( client ) {
            if ( client.id ) {
                RestSrv.edit( clientUrl, client, function () {
                    delete client.password;

                    for ( var i = 0; i < $scope.clients.length; i++ ) {
                        if ( $scope.clients[ i ].id === client.id ) {
                            $scope.clients[ i ] = client;
                        }
                    }

                    $scope.hide();
                    ngNotify.set( 'Cliente \'' + client.name + '\' atualizado.', 'success' );
                } );
            } else {
                RestSrv.add( clientUrl, client, function ( newClient ) {
                    $scope.clients.push( newClient );
                    $scope.hide();
                    ngNotify.set( 'Cliente \'' + client.name + '\' cadastrado', 'success' );
                } );
            }
        };

        var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';

        RestSrv.find( permissionUrl, function ( data ) {
            $scope.permissions = data;

            RestSrv.find( clientUrl, function ( data ) {
                $scope.clients = data;
            } );
        } );

        $scope.inputType = 'password';
  
        $scope.hideShowPassword = function(){
          if ($scope.inputType == 'password')
            $scope.inputType = 'text';
          else
            $scope.inputType = 'password';
        };

		// Método do Angular para realizar as permissões e ocultar alguns Menus
        $scope.hasAnyPermission = function(authorities) {
            var hasPermission = false;
      
            $rootScope.authDetails.permissions.forEach(function(permission) {
              authorities.forEach(function(authority) {
                if (permission.authority === authority) {
                  hasPermission = true;
                }
              });
            });
      
            return hasPermission;
        };
		
		// ArrayList de estados
        $scope.estados = ['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];
} );
