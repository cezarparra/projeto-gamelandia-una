'use strict';
// INSERÇÂO DE DEPENDENCIAS
var gamelandiaJS = angular.module('mutrack');

gamelandiaJS.controller( 'ClientRegisterCtrl', function ($scope, ngNotify, RestSrv, SERVICE_PATH, $http ) {

        $scope.client = {};
        $scope.clients = [];
        $scope.permissions = [];
        $scope.showAddEditClient = false;

        $scope.formRegister = null;
        $scope.redirect = true;

        ngNotify.config( {
            theme: 'pastel'
        } );


        RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
                $scope.games = data;
        } );    
        

        // Show the form used to edit or add clients.
        $scope.show = function () {
            $scope.showAddEditClient = true;
        };

        // Hide the form used to edit or add clients.
        $scope.hide = function () {
            $scope.showAddEditClient = false;
            $scope.client = {};
        };

        // Manage CRUD operations.
        var clientUrl = SERVICE_PATH.PRIVATE_PATH + '/client';

        $scope.editClient = function ( client ) {
            $scope.client = angular.copy( client );
            $scope.show();
        };

        $scope.deleteClient = function ( client ) {
            RestSrv.delete( clientUrl, client, function () {
                $scope.clients.splice( $scope.clients.indexOf( client ), 1 );
                ngNotify.set( 'Cliente \'' + client.name + '\' deletado.', 'success' );
            } );
        };
        
        $scope.saveClient = function ( client ) {
            RestSrv.add( clientUrl, client, function ( newClient ) {
                $scope.clients.push( newClient );

                $scope.formRegister = true;
                $scope.redirect = false;

                ngNotify.set( 'Cliente \'' + client.name + '\' cadastrado com sucesso', 'success' );
            } );

            
        };



        // Request all data (permission and client).
        var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';

        RestSrv.find( permissionUrl, function ( data ) {
            $scope.permissions = data;

            var roleUser = $scope.permissions[1]; 
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

        $scope.estados = ['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS',
        'RO','RR','SC','SP','SE','TO']
} );
