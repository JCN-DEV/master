'use strict';

angular.module('stepApp')
    .factory('BEdApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/bEdApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('BEdApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/bEdApplicationStatusLogs/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });

