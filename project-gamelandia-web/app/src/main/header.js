'user strict';

angular.module('mutrack').
  controller('headerSrv', function($scope, SERVICE_PATH, $rootScope, $location, LoginLogoutSrv, ngNotify, RestSrv ) {

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


    RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
      $scope.login = data;
    } );
   
    
    $scope.logout = function() {
      LoginLogoutSrv.logout();
      ngNotify.set( 'Logout realizado com sucesso.', 'success' );
    };
  });
