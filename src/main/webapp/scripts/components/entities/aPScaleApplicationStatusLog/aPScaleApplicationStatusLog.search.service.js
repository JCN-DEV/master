'use strict';

angular.module('stepApp')
    .factory('APScaleApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/aPScaleApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('APScaleApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/aPScaleApplicationStatusLogsByEmployee/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
