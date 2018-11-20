'use strict';

angular.module('mutrack')
  .service('httpRequestInterceptor', function ($cookies) {
  return {
    request: function (config) {
      config.headers['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');

      config.headers['Access-Control-Allow-Origin'] = $cookies.get('Access-Control-Allow-Origin')

      return config;
    }
  };
});
