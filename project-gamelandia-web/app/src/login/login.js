'use strict';

angular.module('mutrack')
  .controller('LoginCtrl', function($scope, LoginLogoutSrv, RestSrv, $route) {



    $scope.login = function(email, password) {
      LoginLogoutSrv.login(email, password);
    };

    RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
      $scope.logins = data;
      
    } );

  });
