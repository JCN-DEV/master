'use strict';

angular.module('stepApp')
    .factory('MpoApplicationStatusLogSearch', function ($resource) {
        return $resource('api/_search/mpoApplicationStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationLogEmployeeCode', function ($resource) {
        return $resource('api/mpoApplicationStatusLogs/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
