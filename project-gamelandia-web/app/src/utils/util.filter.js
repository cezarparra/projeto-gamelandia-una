'use strict';

angular.module('mutrack')
  .filter('formatPermission', function() {
    return function(input) {
      switch (input) {
        case 'ROLE_ADMIN':
          return 'Administrador';
        break;

        case 'ROLE_USER':
          return 'User';
        break;

        case 'ROLE_EMPLOYEE':
        return 'Funcionário';
      break;

        default:
          return 'Unknown';
        break;
      };
    };
  });
