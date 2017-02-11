'use strict';

angular.module('stepApp')
    .factory('NameCnclApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/nameCnclApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('NameCnclApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/nameCnclApplicationStatusLogs/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
