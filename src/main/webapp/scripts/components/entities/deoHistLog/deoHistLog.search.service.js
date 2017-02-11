'use strict';

angular.module('stepApp')
    .factory('DeoHistLogSearch', function ($resource) {
        return $resource('api/_search/deoHistLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveDeoHistLogs', function ($resource) {
        return $resource('api/deoHistLogs/activeDeos', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
