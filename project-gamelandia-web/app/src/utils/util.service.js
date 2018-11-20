'use strict';

// Arquivo utilizado para o realizar o CRUD
angular.module('mutrack')
  .service('HttpRequestSrv', function($http, ngNotify) {
    return function(url, method, data, callback) {
      var requestParams = {
        method: method,
        url: url,
        headers: { 'Content-Type': 'application/json' },
        data: data
      };

      $http(requestParams).then(
        function successCallback(response) {
          callback && callback(response.data);
        },
        function errorCallback(response) {
            ngNotify.set( response.data.message, 'error' );          
        });
    };
  })
  .service('RestSrv', function(HttpRequestSrv) {
    var restFactory = {};

    // Busca todas as informações
    restFactory.find = function(url, callback) {
      HttpRequestSrv(url, 'GET', {}, callback);
    };

    // Adiciona 
    restFactory.add = function(url, data, callback) {
      HttpRequestSrv(url, 'POST', data, callback);
    };

    // Edita.
    restFactory.edit = function(url, data, callback) {
      HttpRequestSrv(url, 'PUT', data, callback);
    };

    // Deleta
    restFactory.delete = function(url, data, callback) {
      HttpRequestSrv(url, 'DELETE', data, callback);
    };

    return restFactory;
});
